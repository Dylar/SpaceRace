package de.bitb.spacerace.controller

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.Logger
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.ui.base.BaseMenu

class InputHandler(private val game: MainGame) {

    private val inputObserver: MutableList<InputObserver> = ArrayList()

    fun <T : BaseEvent> handleCommand(event: T) {
        //TODO threading
        Gdx.app.postRunnable {
            when {
                event is BaseCommand && event.canExecute(game) -> {
                    event.execute(game)
                    notifyObserver(event)
                }
                else -> notifyObserver(event)
            }
        }
    }

    private fun notifyObserver(event: BaseEvent) {
        val observerList = ArrayList<InputObserver>(inputObserver)
        for (obs in observerList) {
            obs.update(game, event)
        }
    }

    fun addListener(observer: InputObserver) {
        inputObserver.add(observer)
    }

    fun removeListener(observer: InputObserver) {
        inputObserver.remove(observer)
    }

    fun removeListener() {
        inputObserver.clear()
    }

}