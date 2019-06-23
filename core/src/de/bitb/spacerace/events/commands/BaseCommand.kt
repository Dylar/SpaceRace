package de.bitb.spacerace.events.commands

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.objecthandling.DEFAULT
import de.bitb.spacerace.model.objecthandling.DefaultFunction

abstract class BaseCommand(var playerData: PlayerData = NONE_PLAYER_DATA) : DefaultFunction by DEFAULT {

    open fun canExecute(game: MainGame): Boolean {
        return true
    }

    open fun execute(game: MainGame) {

    }
}