package de.bitb.spacerace.events.commands

import de.bitb.spacerace.base.DefaultFunction
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.BaseEvent

abstract class BaseCommand(var playerColor: PlayerColor = PlayerColor.NONE) : BaseEvent(), DefaultFunction by object : DefaultFunction {} {

    abstract fun canExecute(game: MainGame): Boolean
    abstract fun execute(game: MainGame)
}