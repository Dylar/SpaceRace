package de.bitb.spacerace.core.events.commands

import de.bitb.spacerace.grafik.model.player.PlayerColor
import io.reactivex.disposables.CompositeDisposable

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

}