package de.bitb.spacerace.events.commands

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor

abstract class BaseCommand(var playerData: PlayerData = Player.NONE.playerData) : DefaultFunction by object : DefaultFunction {} {

    open fun canExecute(game: MainGame): Boolean {
        return true
    }

    open fun execute(game: MainGame) {

    }
}