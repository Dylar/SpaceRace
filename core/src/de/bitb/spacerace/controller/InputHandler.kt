package de.bitb.spacerace.controller

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.Logger
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.events.commands.BaseCommand
import kotlin.concurrent.thread

class InputHandler(private val game: MainGame) {

    private val inputObserver: MutableList<InputObserver> = ArrayList()

    fun <T : BaseEvent> handleCommand(event: T) {
        Logger.println("handleCommand: ${Thread.currentThread().name}")
        Logger.println(Thread.currentThread().name)
        thread {
            run {
                Logger.println("IN THREAD: ${Thread.currentThread().name}")
                when {
                    event is BaseCommand && event.canExecute(game) -> {
                        event.execute(game)
                        notifyObserver(event)
                    }
                    else -> notifyObserver(event)
                }
            }
        }
    }

    private fun notifyObserver(event: BaseEvent) {
        Gdx.app.postRunnable {
            Logger.println("notifyObserver: ${Thread.currentThread().name}")
            val observerList = ArrayList<InputObserver>(inputObserver)
            for (obs in observerList) {
                obs.update(game, event)
            }
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