package de.bitb.spacerace.core.events.commands

import de.bitb.spacerace.grafik.model.player.PlayerColor
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus

abstract class BaseCommand(
        var player: PlayerColor = PlayerColor.NONE
) {

    protected fun <T> resetOnSuccess(): (T) -> Unit = { reset() }
    protected fun resetOnError(): (Throwable) -> Unit = { it.printStackTrace(); reset() }

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    abstract fun execute()

    fun reset() {
        compositeDisposable.clear()
        CommandPool.addPool(this)
    }

    fun push() {
        EventBus.getDefault().post(this)
    }

}