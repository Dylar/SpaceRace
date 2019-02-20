package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.ui.player.PlayerStats
import de.bitb.spacerace.ui.screens.game.control.DebugControl
import de.bitb.spacerace.ui.screens.game.control.GameControl
import de.bitb.spacerace.ui.screens.game.control.ViewControl

class GameGuiStage(game: MainGame, screen: GameScreen) : BaseGuiStage(screen), InputObserver {

    private var playerStats: PlayerStats = PlayerStats(this)
    private var viewControl: ViewControl = ViewControl(game)
    private var gameControl: GameControl = GameControl(game, this)
    private var debugControl: DebugControl = DebugControl(game)

    init {
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

    override fun <T : BaseCommand> update(game: MainGame, event: T) {

    }

}