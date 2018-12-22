package de.bitb.spacerace.controller

import de.bitb.spacerace.model.events.commands.BaseCommand
import de.bitb.spacerace.model.space.control.BaseSpace
import de.bitb.spacerace.model.space.control.TestSpace

class InputHandler() {

     var space: BaseSpace = TestSpace(this)

    private val inputObserver: MutableList<InputObserver> = ArrayList()

    fun <T : BaseCommand> handleCommand(command: T) {
        if (command.canExecute(space)) {
            command.execute(space)
            for (inputObserver in inputObserver) {
                inputObserver.update(command)
            }
        }
    }

    fun addListener(observer: InputObserver) {
        inputObserver.add(observer)
    }


}