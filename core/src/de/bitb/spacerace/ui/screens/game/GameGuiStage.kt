package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.ui.screens.game.control.GameControl
import de.bitb.spacerace.ui.screens.game.control.ViewControl
import de.bitb.spacerace.ui.player.PlayerStats

class GameGuiStage(screen: GameScreen, inputHandler: InputHandler) : BaseGuiStage(inputHandler) {

    val space: GameController = inputHandler.gameController
    internal var playerStats: PlayerStats = PlayerStats(space)
    private var viewControl: ViewControl = ViewControl(space, screen)
    private var gameControl: GameControl = GameControl(space, this)

    init {
        addActor(playerStats)
        addActor(viewControl)
        addActor(gameControl)
        inputHandler.addListener(gameControl)
        inputHandler.addListener(playerStats)
        playerStats.update()
    }

}