package de.bitb.spacerace.events.commands

import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.model.space.fields.SpaceField

class MoveCommand(val spaceField: SpaceField) : BaseCommand() {

    override fun canExecute(space: GameController): Boolean {
        val playerData = space.playerController.currentPlayer.playerData

        val sameField = playerData.steps.size > 1 && playerData.previousStep == spaceField
        return space.fieldController.hasConnectionTo(playerData.fieldPosition, spaceField) && playerData.phase.isMoving() && (sameField || playerData.steps.size <= playerData.diceResult)
    }

    override fun execute(space: GameController, inputHandler: InputHandler) {
        space.playerController.moveTo(space.playerController.currentPlayer, spaceField)
    }

}