package de.bitb.spacerace.ui.screens.start

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.ui.screens.start.control.MapSelectionControl
import de.bitb.spacerace.ui.screens.start.control.PlayerSelectionControl
import de.bitb.spacerace.ui.screens.start.control.StartButtonControl

class StartGuiStage(screen: StartScreen) : BaseGuiStage(screen) {

    private var gameControlStart = StartButtonControl(gameController, this)
    private var playerSelection = PlayerSelectionControl(gameController, this)
    private var mapSelection = MapSelectionControl(gameController, this)

    init {
        addActor(gameControlStart)
        addActor(playerSelection)
        addActor(mapSelection)
        inputHandler.addListener(gameControlStart)
        inputHandler.addListener(playerSelection)
        inputHandler.addListener(mapSelection)
    }

}