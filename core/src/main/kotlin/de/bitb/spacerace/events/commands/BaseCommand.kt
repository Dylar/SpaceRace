package de.bitb.spacerace.events.commands

import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData

abstract class BaseCommand(
        var playerData: PlayerData = NONE_PLAYER_DATA
) {

    open fun canExecute(): Boolean {
        return true
    }

    open fun execute() {

    }

    open fun reset() {
        CommandPool.addPool(this)
    }

}