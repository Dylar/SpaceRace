package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.model.space.control.BaseSpace
import de.bitb.spacerace.ui.control.GameControl
import de.bitb.spacerace.ui.control.ViewControl
import de.bitb.spacerace.ui.player.PlayerStats

class GameGuiStage(val space: BaseSpace, screen: GameScreen) : BaseGuiStage() {

    internal var playerStats: PlayerStats = PlayerStats(space)
    private var viewControl: ViewControl = ViewControl(space, screen)
    private var gameControl: GameControl = GameControl(space, this)

    init {
        addActor(playerStats)
        addActor(viewControl)
        addActor(gameControl)
    }

}