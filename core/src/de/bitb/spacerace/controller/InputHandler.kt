package de.bitb.spacerace.controller

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import java.util.concurrent.Executors

class InputHandler(private val game: MainGame) {
    private val executor = Executors.newFixedThreadPool(5)!!

    private val inputObserver: MutableList<InputObserver> = ArrayList()

    fun <T : BaseCommand> handleCommand(event: T) {
        val handleCommand = Runnable {
            run {
                if (event.canExecute(game)) {
                    event.execute(game)
                }
                notifyObserver(event)
            }
        }
        executor.execute(handleCommand)
    }

    private fun notifyObserver(event: BaseCommand) {
        Gdx.app.postRunnable {
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