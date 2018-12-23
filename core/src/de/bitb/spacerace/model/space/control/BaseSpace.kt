package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.controller.InputHandler

abstract class BaseSpace(inputHandler: InputHandler) {

    val fieldController = FieldController(this, inputHandler)
    val phaseController = PhaseController(this,  inputHandler)
    val playerController = PlayerController(this, inputHandler)

    abstract fun createSpace()

}

