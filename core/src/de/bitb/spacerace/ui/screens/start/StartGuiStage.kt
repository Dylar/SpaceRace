package de.bitb.spacerace.ui.screens.start

import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.ui.screens.start.control.MapSelectionControl
import de.bitb.spacerace.ui.screens.start.control.PlayerSelectionControl
import de.bitb.spacerace.ui.screens.start.control.StartButtonControl
import de.bitb.spacerace.ui.screens.start.control.TestFieldSelectionControl

class StartGuiStage(screen: StartScreen) : BaseGuiStage(screen) {

    private var startButtonControl = StartButtonControl(this)
    private var playerSelection = PlayerSelectionControl(gameController, this)
    private var mapSelection = MapSelectionControl(gameController, this)
    private var testFieldSelectionControl = TestFieldSelectionControl(gameController, this)

    init {
        startButtonControl.setPosition(Dimensions.SCREEN_WIDTH / 2 - startButtonControl.width / 2, Dimensions.SCREEN_HEIGHT / 1.5f - startButtonControl.height / 2)
        playerSelection.setPosition(0f, Dimensions.SCREEN_HEIGHT / 2f - playerSelection.height / 2)
        mapSelection.setPosition(Dimensions.SCREEN_WIDTH - mapSelection.width, Dimensions.SCREEN_HEIGHT / 2f - mapSelection.height / 2)
        testFieldSelectionControl.setPosition(0f, Dimensions.SCREEN_HEIGHT / 2f - testFieldSelectionControl.height / 2)

        addActor(startButtonControl)
        addActor(playerSelection)
        addActor(mapSelection)
        addActor(testFieldSelectionControl)
        inputHandler.addListener(startButtonControl)
        inputHandler.addListener(playerSelection)
        inputHandler.addListener(mapSelection)
    }

}