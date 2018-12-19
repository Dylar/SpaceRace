package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.controller.History

abstract class BaseSpace {

    val history: History = History()

    val fieldController = FieldController(this)
    val phaseController = PhaseController(this)
    val playerController = PlayerController(this)

    init {
        createSpace()
    }

    abstract fun createSpace()

}

