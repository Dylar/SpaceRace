package de.bitb.spacerace.core.controller

import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.core.events.GameOverEvent
import de.bitb.spacerace.core.events.OpenEndRoundMenuEvent
import de.bitb.spacerace.usecase.game.observe.ObserveAttachItemUseCase
import de.bitb.spacerace.usecase.game.observe.ObserveRoundUsecase
import de.bitb.spacerace.usecase.game.observe.ObserveWinnerUsecase
import de.bitb.spacerace.core.utils.Logger
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameController
@Inject constructor(
        var observeWinnerUsecase: ObserveWinnerUsecase,
        var observeRoundUsecase: ObserveRoundUsecase,
        var observeAttachItemUseCase: ObserveAttachItemUseCase,
        var playerController: PlayerController,
        var graphicController: GraphicController
) {
    private val compositeDisposable = CompositeDisposable()

    fun clear() {
        compositeDisposable.clear()
        playerController.clear()
        graphicController.clearGraphics()
    }

    fun initGameObserver() {
        initWinnerObserver()
        initPhaseObserver()
        playerController.initObserver()

        initAttachPlayerObserver()
    }

    fun initWinnerObserver() {
        compositeDisposable += observeWinnerUsecase.observeStream(
                params = WIN_AMOUNT,
                onNext = { winner ->
                    Logger.printLog("AND THE WINNER IIIIISSS: $winner")
                    EventBus.getDefault().post(GameOverEvent(winner.playerColor))
                })
    }

    fun initPhaseObserver() {
        compositeDisposable += observeRoundUsecase.observeStream { roundEnd ->
            if (roundEnd) EventBus.getDefault().post(OpenEndRoundMenuEvent())
        }
    }

    fun initAttachPlayerObserver() {
        compositeDisposable += observeAttachItemUseCase.observeStream { (player, items) ->
            attachToPlayer(player, items)
        }
    }

    private fun attachToPlayer(player: PlayerData, items: List<ItemData>) {
        val fieldGraphic = graphicController.getFieldGraphic(player.positionField.target.gamePosition)
        val itemGraphic = fieldGraphic.disposedItems.first { it.itemType == items.first().itemInfo.type }
        val playerGraphic = graphicController.getPlayerGraphic(player.playerColor)
        fieldGraphic.removeItem(itemGraphic)
        itemGraphic.itemImage.setRotating(itemGraphic, playerGraphic.playerImage, playerGraphic.playerImage.width * 0.7)
    }

}