package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame

abstract class GameController(game: MainGame) {
    val inputHandler = InputHandler(game)
    val fieldController = FieldController()
    val phaseController = PhaseController()
    val playerController = PlayerController()

    abstract fun createSpace()
}

