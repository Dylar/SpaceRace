package de.bitb.spacerace.ui.screens.start

import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.ui.screens.game.BackgroundStage


class StartScreen(game: MainGame) : BaseScreen(game) {

    override fun createGuiStage(): BaseStage {
        return StartGuiStage(this)
    }

    override fun createBackgroundStage(): BaseStage {
        return BackgroundStage(this)
    }

}
