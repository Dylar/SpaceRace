package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.ui.player.PlayerStatsGui
import de.bitb.spacerace.ui.screens.game.control.DebugGui
import de.bitb.spacerace.ui.screens.game.control.GameActionGui
import de.bitb.spacerace.ui.screens.game.control.ViewControlGui
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class GameGuiStage(
        screen: GameScreen
) : BaseGuiStage(screen) {

    @Inject
    protected lateinit var observeCurrentPlayerUseCase: ObserveCurrentPlayerUseCase

    private var dispo: Disposable? = null

    private var playerStatsGui: PlayerStatsGui = PlayerStatsGui(this)
    private var viewControlGui: ViewControlGui = ViewControlGui(screen)
    private var gameActionGui: GameActionGui = GameActionGui(this)
    private var debugGui: DebugGui = DebugGui(screen)

    init {
        MainGame.appComponent.inject(this)
        listenToUpdate()

        addActor(playerStatsGui)
        addActor(viewControlGui)
        addActor(gameActionGui)
        addActor(debugGui)
        debugGui.x = viewControlGui.width
    }

    private fun listenToUpdate() {
        dispo?.dispose()
        dispo = observeCurrentPlayerUseCase.observeStream(
                onNext = {
                    //                    Logger.println("observeCurrentPlayerUseCase NEXT:\n$it")
                    playerStatsGui.update(it)
                })
    }

}