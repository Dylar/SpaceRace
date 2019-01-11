package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField

class MoveCommand(val spaceField: SpaceField, playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        val playerData = getPlayerData(game, playerColor)
        val sameField = playerData.steps.size > 1 && playerData.previousStep == spaceField
        val hasConnection = getPlayerField(game, playerColor).hasConnectionTo(spaceField) // mach das mit position data TODO
        return hasConnection && (sameField && playerData.phase.isMoving() || playerData.canMove())
    }

    override fun execute(game: MainGame) {
        game.gameController.playerController.moveTo(spaceField, getPlayer(game, playerColor))
    }

}