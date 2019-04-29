package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.ui.player.PlayerStats
import de.bitb.spacerace.ui.screens.game.control.DebugControl
import de.bitb.spacerace.ui.screens.game.control.GameControl
import de.bitb.spacerace.ui.screens.game.control.ViewControl
import de.bitb.spacerace.usecase.ui.PlayerChangedUsecase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class GameGuiStage(game: MainGame, screen: GameScreen) : BaseGuiStage(screen), InputObserver {

    private val compositDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    protected lateinit var playerChangedUsecase: PlayerChangedUsecase

    private var playerStats: PlayerStats = PlayerStats(this)
    private var viewControl: ViewControl = ViewControl(game)
    private var gameControl: GameControl = GameControl(game, this)
    private var debugControl: DebugControl = DebugControl(game)

    init {
        MainGame.appComponent.inject(this)
        listenToUpdate()

        addActor(playerStats)
        addActor(viewControl)
        addActor(gameControl)
        addActor(debugControl)
        debugControl.x = viewControl.width

        inputHandler.addListener(gameControl)
        inputHandler.addListener(playerStats)
        inputHandler.addListener(viewControl)
        inputHandler.addListener(this)
    }

    private fun listenToUpdate() {
        compositDisposable += playerChangedUsecase(
                onNext = {
                    Logger.println("NEXT: playerChangedUsecase: $it")
                    playerStats.changePlayerColor(it)
                },
                onError = {
                    Logger.println("ERROR: playerChangedUsecase: $it")
                })
    }

    override fun <T : BaseCommand> update(game: MainGame, event: T) {

    }

}