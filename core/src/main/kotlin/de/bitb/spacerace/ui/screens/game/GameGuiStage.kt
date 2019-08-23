package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.ui.player.PlayerStats
import de.bitb.spacerace.ui.screens.game.control.DebugControl
import de.bitb.spacerace.ui.screens.game.control.GameControl
import de.bitb.spacerace.ui.screens.game.control.ViewControl
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class GameGuiStage(screen: GameScreen) : BaseGuiStage(screen) {

    @Inject
    protected lateinit var observeCurrentPlayerUseCase: ObserveCurrentPlayerUseCase

    private var dispo: Disposable? = null

    private var playerStats: PlayerStats = PlayerStats(this)
    private var viewControl: ViewControl = ViewControl(screen)
    private var gameControl: GameControl = GameControl(this)
    private var debugControl: DebugControl = DebugControl(screen)

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
    }

    private fun listenToUpdate() {
        dispo?.dispose()
        dispo = observeCurrentPlayerUseCase.observeStream(
                onNext = {
//                    Logger.println("observeCurrentPlayerUseCase NEXT:\n$it")
                    playerStats.update(it)
                })
    }

}