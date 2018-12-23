package de.bitb.spacerace.controller

import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.model.space.control.TestSpace

class InputHandler() {

    var space: GameController = TestSpace(this)

    private val inputObserver: MutableList<InputObserver> = ArrayList()

    fun <T : BaseEvent> handleCommand(event: T) {
        if (event is BaseCommand) {
            if (event.canExecute(space)) {
                event.execute(space, this)
                notifyObserver(event)
            }
        } else {
            notifyObserver(event)
        }

    }

    fun notifyObserver(event: BaseEvent) {
        for (inputObserver in inputObserver) {
            inputObserver.update(event)
        }
    }

    fun addListener(observer: InputObserver) {
        inputObserver.add(observer)
    }

    fun removeListener() {
        inputObserver.clear()
    }

}