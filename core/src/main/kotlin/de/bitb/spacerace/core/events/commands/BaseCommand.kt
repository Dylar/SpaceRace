package de.bitb.spacerace.core.events.commands

import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.DisposableHandler
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus

abstract class BaseCommand(
        var player: PlayerColor = PlayerColor.NONE
) : DisposableHandler {

    protected fun <T> resetOnSuccess(): (T) -> Unit = { reset() }
    protected fun resetOnError(): (Throwable) -> Unit = { it.printStackTrace(); reset() }

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    abstract fun execute()

    fun reset() {
        disposeDisposables()
        CommandPool.addPool(this)
    }

    fun push() {
        EventBus.getDefault().post(this)
    }

}