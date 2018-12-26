package de.bitb.spacerace.base

import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.ui.base.GuiComponent

abstract class BaseGuiStage(val screen: BaseScreen,
                            val gameController: GameController = screen.game.gameController,
                            val inputHandler: InputHandler = gameController.inputHandler,
                            viewport: Viewport = ScreenViewport()) : BaseStage(viewport), GuiComponent {
}