package de.bitb.spacerace.controller

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.Logger
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ui.PlayerChangedUsecase
import io.reactivex.rxkotlin.subscribeBy
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.concurrent.Executors
import javax.inject.Inject


class InputHandler(private val game: MainGame) : DefaultFunction {

    @Inject
    protected lateinit var playerChangedUsecase: PlayerChangedUsecase

    @Inject
    protected lateinit var playerDataSource: PlayerDataSource

    private val executor = Executors.newFixedThreadPool(5)!!

    private val inputObserver: MutableList<InputObserver> = ArrayList()

    init {
        EventBus.getDefault().register(this)
        MainGame.appComponent.inject(this)
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

                playerDataSource
                        .insertAll(getCurrentPlayer(game).playerData)
                        .map { it.first() }
                        .subscribeBy(
                                onSuccess = {
                                    Logger.println("CurrentPlayer updated: $it")
                                })
                notifyObserver(event)
            }
        }
        executor.execute(handleCommand)
    }

}