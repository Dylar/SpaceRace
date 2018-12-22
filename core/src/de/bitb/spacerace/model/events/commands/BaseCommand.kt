package de.bitb.spacerace.model.events.commands

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.model.events.BaseEvent
import de.bitb.spacerace.model.space.control.BaseSpace

abstract class BaseCommand(var playerColor: PlayerColor = PlayerColor.NONE) : BaseEvent() {

    abstract fun canExecute(space: BaseSpace): Boolean
    abstract fun execute(space: BaseSpace)
}