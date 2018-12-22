package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.controller.History
import de.bitb.spacerace.controller.InputHandler

abstract class BaseSpace(inputHandler: InputHandler) {

//    val history: History = History()

    val fieldController = FieldController(this, inputHandler)
    val phaseController = PhaseController(this,  inputHandler)
    val playerController = PlayerController(this, inputHandler)

    init {
        createSpace()
    }

    abstract fun createSpace()

}

