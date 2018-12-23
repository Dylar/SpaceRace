package de.bitb.spacerace.ui.screens.start

import de.bitb.spacerace.base.BaseGame
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.ui.screens.game.BackgroundStage


class StartScreen(game: BaseGame, inputHandler: InputHandler = InputHandler()) : BaseScreen(game,inputHandler) {

    override fun createGuiStage(): BaseStage {
        return StartGuiStage(this, inputHandler)
    }

    override fun createBackgroundStage(): BaseStage {
        return BackgroundStage(this)
    }

}
