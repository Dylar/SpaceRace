package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.ui.screens.game.control.GameControl
import de.bitb.spacerace.ui.screens.game.control.ViewControl
import de.bitb.spacerace.ui.player.PlayerStats

class GameGuiStage(game: MainGame, screen: GameScreen) : BaseGuiStage(screen) {

    private var playerStats: PlayerStats = PlayerStats(this)
    private var viewControl: ViewControl = ViewControl(game)
    private var gameControl: GameControl = GameControl(this)

    init {
        addActor(playerStats)
        addActor(viewControl)
        addActor(gameControl)
        inputHandler.addListener(gameControl)
        inputHandler.addListener(playerStats)
    }

}