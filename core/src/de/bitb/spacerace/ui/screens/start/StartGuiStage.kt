package de.bitb.spacerace.ui.screens.start

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.start.StartGameCommand
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.ui.screens.start.control.StartButtonControl

class StartGuiStage(val screen: StartScreen, inputHandler: InputHandler) : BaseGuiStage(inputHandler), InputObserver {

    val space: GameController = inputHandler.gameController
    private var gameControlStart = StartButtonControl(space, this)

    init {
        addActor(gameControlStart)
        inputHandler.addListener(gameControlStart)
        inputHandler.addListener(this)
    }

    override fun <T : BaseEvent> update(event: T) {
        if (event is StartGameCommand) {
            inputHandler.removeListener()
            screen.game.changeScreen(GameScreen(screen.game, inputHandler))
        }
    }

}