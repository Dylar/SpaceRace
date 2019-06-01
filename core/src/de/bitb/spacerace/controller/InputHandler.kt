package de.bitb.spacerace.controller

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.usecase.ui.CommandUsecase
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class InputHandler(private val game: MainGame) : DefaultFunction {

    @Inject
    protected lateinit var commandUsecase: CommandUsecase

    private val inputObserver: MutableList<InputObserver> = ArrayList()

    init {
        EventBus.getDefault().register(this)
        MainGame.appComponent.inject(this)

        commandUsecase.getResult( //TODO just execute
                game,
                onSuccess = {
                    notifyObserver(it)
                },
                onError = {
                    it.printStackTrace()
                }
        )
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
        commandUsecase.publishUpdate(event)
    }

    private fun notifyObserver(event: BaseCommand) {
        Gdx.app.postRunnable {
            val observerList = ArrayList<InputObserver>(inputObserver)
            for (obs in observerList) {
                obs.update(game, event)
            }
        }
    }

}