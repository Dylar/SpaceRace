package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.fields.SpaceField

class MoveCommand(val spaceField: SpaceField) : BaseCommand() {

    override fun canExecute(game: MainGame): Boolean {
        val playerData = getPlayerData(game, playerColor)
        val sameField = playerData.steps.size > 1 && playerData.previousStep == spaceField
        return game.gameController.fieldController.hasConnectionTo(playerData.fieldPosition, spaceField) && (sameField && playerData.phase.isMoving() || playerData.canMove())
    }

    override fun execute(game: MainGame) {
        game.gameController.playerController.moveTo(spaceField, getPlayerData(game, playerColor))
    }

}