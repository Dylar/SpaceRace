package de.bitb.spacerace.events.commands

import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.space.control.GameController

class DiceCommand() : BaseCommand() {

    override fun canExecute(space: GameController): Boolean {
        return !space.playerController.canDice(playerColor)
    }

    override fun execute(space: GameController, inputHandler: InputHandler) {
        space.playerController.dice()
    }

}