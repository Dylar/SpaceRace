package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.Logger
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceField

class MoveCommand(val spaceField: SpaceField, playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        val playerData = getPlayerData(game, playerColor)
        val sameField = playerData.steps.size > 1 && playerData.previousStep.isPosition(spaceField.positionData)
        val hasConnection = getPlayerField(game, playerColor).hasConnectionTo(spaceField)
        return hasConnection && (sameField && playerData.phase.isMoving() || playerData.canMove())
    }

    override fun execute(game: MainGame) {
        val player = getPlayer(game, playerColor)

        setSteps(player.playerData, spaceField)

        val playerImage = player.playerImage
        val fieldImage = spaceField.fieldImage
        if (fieldImage.movingState == MovingState.ROTATE_POINT) {
            player.positionData.setPosition(spaceField.positionData)
            playerImage.moveToPoint(player,
                    fieldImage.getRotationPosition(playerImage, spaceField.positionData),
                    fieldImage.getRunnableAction(Runnable {
                        playerImage.setRotationFollow(fieldImage)
                    }),
                    fieldImage.getRotationAction(playerImage)
            )
        } else {
            playerImage.moveTo(player, spaceField.positionData, playerImage.getRunnableAction(Runnable {
                playerImage.setRotationFollow(null)
                playerImage.movingState = MovingState.NONE//TODO queue not working?
            }))
        }
        Logger.println("Player Field: ${spaceField.id}, ${spaceField.fieldType.name}")

    }


    private fun setSteps(playerData: PlayerData, spaceField: SpaceField) {
        val sameField = previousFieldSelected(playerData, spaceField)
        if (sameField) {
            playerData.steps.removeAt(playerData.steps.size - 1)
        } else {
            playerData.steps.add(spaceField.positionData)
        }
    }

    private fun previousFieldSelected(playerData: PlayerData, spaceField: SpaceField): Boolean {
        return playerData.steps.size > 1 && playerData.previousStep.isPosition(spaceField.positionData)
    }


}