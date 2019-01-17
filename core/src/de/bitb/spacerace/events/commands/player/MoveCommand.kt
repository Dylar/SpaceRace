package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceField

class MoveCommand(val spaceField: SpaceField, playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        val playerData = getPlayerData(game, playerColor)
        val sameField = playerData.steps.size > 1 && playerData.previousStep.isPosition(spaceField.gamePosition)
        val hasConnection = getPlayerField(game, playerColor).hasConnectionTo(spaceField)
        return hasConnection && (sameField && playerData.phase.isMoving() || playerData.canMove())
    }

    override fun execute(game: MainGame) {
        val player = getPlayer(game, playerColor)


        val playerImage = player.playerImage
        val fieldImage = spaceField.fieldImage

        playerImage.moveToPoint(player, fieldImage, playerImage.getNONEAction(playerImage, fieldImage))

        player.playerData.setSteps(player.playerData, spaceField)
        player.gamePosition.setPosition(spaceField.gamePosition)

        Logger.println("Player Field: ${spaceField.id}, ${spaceField.fieldType.name}")
    }


}