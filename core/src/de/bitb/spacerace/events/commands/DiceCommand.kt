package de.bitb.spacerace.events.commands

import de.bitb.spacerace.model.space.control.BaseSpace

class DiceCommand() : BaseCommand() {

    override fun canExecute(space: BaseSpace): Boolean {
        return !space.playerController.canDice(playerColor)
    }

    override fun execute(space: BaseSpace) {
        space.playerController.dice()
    }

}