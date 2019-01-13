package de.bitb.spacerace.events.commands

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.PlayerColor

abstract class BaseCommand(var playerColor: PlayerColor = PlayerColor.NONE) : DefaultFunction by object : DefaultFunction {} {

    abstract fun canExecute(game: MainGame): Boolean
    abstract fun execute(game: MainGame)
}