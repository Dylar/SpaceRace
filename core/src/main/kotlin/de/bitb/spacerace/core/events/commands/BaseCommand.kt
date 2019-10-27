package de.bitb.spacerace.core.events.commands

import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData
import io.reactivex.disposables.CompositeDisposable

abstract class BaseCommand(
        var DONT_USE_THIS_PLAYER_DATA: PlayerData = NONE_PLAYER_DATA
) {
    protected fun <T> resetOnSuccess(): (T) -> Unit = { reset() }
    protected fun resetOnError(): (Throwable) -> Unit = { it.printStackTrace(); reset() }

    protected val compositDisposable: CompositeDisposable = CompositeDisposable()
    open fun canExecute(): Boolean {
        return true
    }

    open fun execute() {

    }

    fun reset() {
        compositDisposable.clear()
        CommandPool.addPool(this)
    }

}