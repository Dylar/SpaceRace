package de.bitb.spacerace.events.commands

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.space.fields.SpaceField

class MoveCommand(val spaceField: SpaceField) : BaseCommand() {

    override fun canExecute(game: MainGame): Boolean {
        val gameController = game.gameController
        val playerData = gameController.playerController.currentPlayer.playerData

        val sameField = playerData.steps.size > 1 && playerData.previousStep == spaceField
        return gameController.fieldController.hasConnectionTo(playerData.fieldPosition, spaceField) && playerData.phase.isMoving() && (sameField || playerData.steps.size <= playerData.diceResult)
    }

    override fun execute(game: MainGame) {
        val gameController = game.gameController
        gameController.playerController.moveTo(gameController.playerController.currentPlayer, spaceField)
    }

}