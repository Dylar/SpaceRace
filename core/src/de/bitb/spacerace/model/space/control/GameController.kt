package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.controller.InputHandler

abstract class GameController(inputHandler: InputHandler) {

    val fieldController = FieldController(inputHandler)
    val phaseController = PhaseController()
    val playerController = PlayerController()
    abstract fun createSpace()
}

