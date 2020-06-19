package de.bitb.spacerace.core.controller

import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.core.events.GameOverEvent
import de.bitb.spacerace.core.utils.Logger
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.ui.screens.GuiNavi
import de.bitb.spacerace.usecase.DisposableHandler
import de.bitb.spacerace.usecase.game.observe.*
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameController
@Inject constructor(
        var observeWinnerUsecase: ObserveWinnerUsecase,
        var observeRoundUsecase: ObserveRoundUsecase,
        var observeAttachItemUseCase: ObserveAttachItemUseCase,
        var observeRemoveItemUseCase: ObserveRemoveItemUseCase,
        var observeMoveItemUseCase: ObserveMoveItemUseCase,
        var playerController: PlayerController,
        var graphicController: GraphicController
) : DisposableHandler {
    override val compositeDisposable = CompositeDisposable()

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
        initRemoveItemObserver()
        initMovingItemObserver()
    }

    fun initWinnerObserver() {
        observeWinnerUsecase.observeStream(
                params = WIN_AMOUNT,
                onNext = { winner ->
                    Logger.justPrint("AND THE WINNER IIIIISSS: $winner")
                    EventBus.getDefault().post(GameOverEvent(winner.playerColor))
                })
                .addDisposable()
    }

    fun initPhaseObserver() {
        observeRoundUsecase.observeStream { result ->
            if (result.roundEnding) EventBus.getDefault().post(GuiNavi.EndRoundMenu())
        }.addDisposable()
    }

    fun initAttachPlayerObserver() {
         observeAttachItemUseCase.observeStream { (player, items) ->
            attachItemToPlayer(player, items)
        }.addDisposable()
    }

    fun initRemoveItemObserver() {
        observeRemoveItemUseCase.observeStream { (player, field, items) ->
            if (player != null) {
                removeItemFromPlayer(player, items)
            }
//            else if (field != null) { TODO
//                removeItemFromField(field, items)
//            }
        }.addDisposable()
    }

    fun initMovingItemObserver() {
        observeMoveItemUseCase.observeStream { movingItems ->
            movingItems.forEach {
                graphicController.moveItems(it.fromField, it.toField, it.item)
            }
        }.addDisposable()
    }

    private fun removeItemFromPlayer(player: PlayerData, items: List<ItemData>) {
        val playerGraphic = graphicController.getPlayerGraphic(player.playerColor)
        val itemGraphic = playerGraphic.attachedItems.first { it.itemType == items.first().itemInfo.type }
        playerGraphic.attachedItems.remove(itemGraphic)
        //TODO remove all items
        itemGraphic.getGameImage().remove()
    }

    private fun attachItemToPlayer(player: PlayerData, items: List<ItemData>) {
        val fieldGraphic = graphicController.getFieldGraphic(player.positionField.target.gamePosition)
        val itemGraphic = fieldGraphic.disposedItems.first { it.itemType == items.first().itemInfo.type }
        //TODO add all items
        val playerGraphic = graphicController.getPlayerGraphic(player.playerColor)
        fieldGraphic.removeItem(itemGraphic)
        playerGraphic.attachedItems.add(itemGraphic)
        itemGraphic.itemImage.setRotating(itemGraphic, playerGraphic.playerImage, playerGraphic.playerImage.width * 0.7)
    }

}