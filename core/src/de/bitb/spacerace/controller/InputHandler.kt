package de.bitb.spacerace.controller

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.BaseCommand

class InputHandler(private val game: MainGame) {

    private val inputObserver: MutableList<InputObserver> = ArrayList()

    fun <T : BaseEvent> handleCommand(event: T) {
        when {
            event is BaseCommand && !event.canExecute(game) -> return
            event is BaseCommand -> event.execute(game)
        }
        notifyObserver(event)
    }

    private fun notifyObserver(event: BaseEvent) {
        for (inputObserver in inputObserver) {
            inputObserver.update(game, event)
        }
    }

    fun addListener(observer: InputObserver) {
        inputObserver.add(observer)
    }

    fun removeListener() {
        inputObserver.clear()
    }

}