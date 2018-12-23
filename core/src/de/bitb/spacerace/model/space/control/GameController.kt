package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.controller.InputHandler

abstract class GameController() {

    val fieldController = FieldController()
    val phaseController = PhaseController()
    val playerController = PlayerController()

    abstract fun createSpace(inputHandler: InputHandler)
}

