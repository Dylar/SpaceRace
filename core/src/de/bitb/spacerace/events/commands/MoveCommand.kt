package de.bitb.spacerace.events.commands

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.space.fields.SpaceField

class MoveCommand(val spaceField: SpaceField) : BaseCommand() {

    override fun canExecute(game: MainGame): Boolean {
        val gameController = game.gameController
        val playerData = gameController.playerController.currentPlayer.playerData

        val sameField = playerData.steps.size > 1 && playerData.previousStep == spaceField
        return gameController.fieldController.hasConnectionTo(playerData.fieldPosition, spaceField) && (sameField && playerData.phase.isMoving() || playerData.canMove())
    }

    override fun execute(game: MainGame) {
        val playerController = game.gameController.playerController
        playerController.moveTo(playerController.currentPlayer, spaceField)
    }

}