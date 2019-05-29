package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.ui.player.PlayerStats
import de.bitb.spacerace.ui.screens.game.control.DebugControl
import de.bitb.spacerace.ui.screens.game.control.GameControl
import de.bitb.spacerace.ui.screens.game.control.ViewControl
import de.bitb.spacerace.usecase.game.ObservePlayerUsecase
import de.bitb.spacerace.usecase.game.PlayerTurnChangedUsecase
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class GameGuiStage(game: MainGame, screen: GameScreen) : BaseGuiStage(screen), InputObserver {

    private var dbDisposable: Disposable? = null

    @Inject
    protected lateinit var playerTurnChangedUsecase: PlayerTurnChangedUsecase

    @Inject
    protected lateinit var observePlayerUsecase: ObservePlayerUsecase

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
//        inputHandler.addListener(playerStats)
        inputHandler.addListener(viewControl)
        inputHandler.addListener(this)
    }

    private fun changeColor(playerData: PlayerData) {
        dbDisposable?.dispose()
        dbDisposable = observePlayerUsecase.observeStream(
                params = playerData.playerColor,
                onNext = {
                    Logger.println("UPDATE: updatePlayerUsecase: $it")
                    playerStats.changePlayerColor(it.first())
                },
                onError = {
                    Logger.println("ERROR: updatePlayerUsecase: $it")
                }
        )
    }

    private fun listenToUpdate() {
        compositDisposable += playerTurnChangedUsecase.observeStream(
                onNext = {
                    Logger.println("NEXT: playerTurnChangedUsecase: $it")
                    changeColor(it)
                },
                onError = {
                    Logger.println("ERROR: playerTurnChangedUsecase: $it")
                })
    }

    override fun <T : BaseCommand> update(game: MainGame, event: T) {

    }

}