package de.bitb.spacerace.controller

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InputHandler
@Inject constructor(
) : DefaultFunction {

    private val inputObserver: MutableList<InputObserver> = ArrayList() //TODO delete me

    fun addListener(observer: InputObserver) {
        inputObserver.add(observer)
    }

    fun removeListener(observer: InputObserver) {
        inputObserver.remove(observer)
    }

    fun removeListener() {
        inputObserver.clear()
    }

    fun notifyObserver(event: BaseCommand) {
        Gdx.app.postRunnable {
            val observerList = ArrayList(inputObserver)
            for (obs in observerList) {
                obs.update(event)
            }
        }
    }

}