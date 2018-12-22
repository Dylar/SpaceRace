package de.bitb.spacerace.model.events.commands

import de.bitb.spacerace.model.space.control.BaseSpace

class DiceCommand() : BaseCommand() {

    override fun execute(space: BaseSpace) {
        space.playerController.dice()
    }

    override fun canExecute(space: BaseSpace): Boolean {
        return !space.playerController.canDice(playerColor)
    }
}