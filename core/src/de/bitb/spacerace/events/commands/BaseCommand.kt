package de.bitb.spacerace.events.commands

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.PlayerColor

abstract class BaseCommand(var playerColor: PlayerColor = PlayerColor.NONE) : DefaultFunction by object : DefaultFunction {} {

    open fun canExecute(game: MainGame): Boolean {
        return true
    }

    open fun execute(game: MainGame) {

    }
}