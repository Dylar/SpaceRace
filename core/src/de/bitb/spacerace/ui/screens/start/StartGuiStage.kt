package de.bitb.spacerace.ui.screens.start

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.controller.InputObserver
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.start.StartGameCommand
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.ui.screens.start.control.PlayerSelectionControl
import de.bitb.spacerace.ui.screens.start.control.StartButtonControl

class StartGuiStage(screen: StartScreen) : BaseGuiStage(screen), InputObserver {

    private var gameControlStart = StartButtonControl(gameController, this)
    private var layerSelection = PlayerSelectionControl(gameController, this)


    init {
        addActor(gameControlStart)
        addActor(layerSelection)
        inputHandler.addListener(gameControlStart)
        inputHandler.addListener(this)
    }

    override fun <T : BaseEvent> update(game: MainGame, event: T) {
        if (event is StartGameCommand) {
            inputHandler.removeListener()
            screen.game.changeScreen(GameScreen(screen.game))
        }
    }

}