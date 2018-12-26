package de.bitb.spacerace.events.commands

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.model.space.control.GameController

abstract class BaseCommand(var playerColor: PlayerColor = PlayerColor.NONE) : BaseEvent() {

    abstract fun canExecute(game: MainGame): Boolean
    abstract fun execute(game: MainGame)
}