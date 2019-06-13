package de.bitb.spacerace.controller

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.Logger
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.Executors
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe


class InputHandler(private val game: MainGame) {
    private val executor = Executors.newFixedThreadPool(5)!!

    private val inputObserver: MutableList<InputObserver> = ArrayList()

    init {
        EventBus.getDefault().register(this)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun receiveCommand(event: BaseCommand) {
        Logger.println(event::class.java.simpleName)
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

}