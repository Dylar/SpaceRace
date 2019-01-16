package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.Logger
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.disposable.moving.MovingState
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

        setSteps(player.playerData, spaceField)

        val playerImage = player.playerImage
        val fieldImage = spaceField.fieldImage

        var target: PositionData = fieldImage.imagePosition.copy()
        val action = if (fieldImage.movingState == MovingState.ROTATE_POINT) {
            val point = fieldImage.getRotationPoint(playerImage, spaceField.getGameImage(), fieldImage.getRotationAngle())
            target = PositionData(point.x, point.y, fieldImage.width, fieldImage.height)

            playerImage.getNONEAction(playerImage, fieldImage)
        } else { //TODO mach das komplett in move or image or sowas
            val playerPosition = playerImage.imagePosition.copy()
            target.centerPosition(playerPosition.width, playerPosition.height)
            getRunnableAction(Runnable {
                playerImage.movingState = MovingState.NONE
            })
        }
        playerImage.moveTo(player, target, spaceField.gamePosition, action)
        Logger.println("Player Field: ${spaceField.id}, ${spaceField.fieldType.name}")
    }


    private fun setSteps(playerData: PlayerData, spaceField: SpaceField) {
        val sameField = previousFieldSelected(playerData, spaceField)
        if (sameField) {
            playerData.steps.removeAt(playerData.steps.size - 1)
        } else {
            playerData.steps.add(spaceField.gamePosition)
        }
    }//TODO mach das in playerdata oder so

    private fun previousFieldSelected(playerData: PlayerData, spaceField: SpaceField): Boolean {
        return playerData.steps.size > 1 && playerData.previousStep.isPosition(spaceField.gamePosition)
    }


}