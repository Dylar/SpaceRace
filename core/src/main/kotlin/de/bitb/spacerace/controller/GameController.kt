package de.bitb.spacerace.controller

import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.events.GameOverEvent
import de.bitb.spacerace.events.OpenEndRoundMenuEvent
import de.bitb.spacerace.usecase.game.observe.ObserveRoundUsecase
import de.bitb.spacerace.usecase.game.observe.ObserveWinnerUsecase
import de.bitb.spacerace.usecase.ui.CommandUsecase
import de.bitb.spacerace.utils.Logger
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
        var playerController: PlayerController,
        var graphicController: GraphicController
) {
    private val compositeDisposable = CompositeDisposable()

    fun clear() {
        compositeDisposable.clear()
        playerController.clear()
    }
    fun initGameObserver() {
        initWinnerObserver()
        initPhaseObserver()
        playerController.initObserver()
    }

    fun initWinnerObserver() {
        compositeDisposable += observeWinnerUsecase.observeStream(
                params = WIN_AMOUNT,
                onNext = { winner ->
                    Logger.println("AND THE WINNER IIIIISSS: $winner")
                    EventBus.getDefault().post(GameOverEvent(winner.playerColor))
                })
    }

    fun initPhaseObserver() {
        compositeDisposable += observeRoundUsecase.observeStream { roundEnd ->
            if (roundEnd) EventBus.getDefault().post(OpenEndRoundMenuEvent())
        }
    }

}