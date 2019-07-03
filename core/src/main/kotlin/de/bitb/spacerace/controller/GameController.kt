package de.bitb.spacerace.controller

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.gameover.GameOverCommand
import de.bitb.spacerace.model.objecthandling.DEFAULT
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.ui.screens.game.GameStage
import de.bitb.spacerace.usecase.game.ObserveRoundUsecase
import de.bitb.spacerace.usecase.game.ObserveWinnerUsecase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class GameController() : DefaultFunction by DEFAULT {

    val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var observeWinnerUsecase: ObserveWinnerUsecase

    @Inject
    lateinit var observeRoundUsecase: ObserveRoundUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    private fun initWinnerObserver() {
        compositeDisposable += observeWinnerUsecase.observeStream(
                params = WIN_AMOUNT,
                onNext = { winner ->
                    Logger.println("AND THE WINNER IIIIISSS: $winner")
                    EventBus.getDefault().post(GameOverCommand(winner.playerColor))
                })
    }

    private fun initPhaseObserver() {
        compositeDisposable += observeRoundUsecase.observeStream(
                onNext = { isNext ->
                    Logger.println("observeRoundUsecase: NEXT ROUND: $isNext")
                })
    }

}

