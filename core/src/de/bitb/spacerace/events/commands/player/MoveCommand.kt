package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.Logger
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.usecase.game.UpdatePlayerUsecase
import javax.inject.Inject

class MoveCommand(val spaceField: SpaceField, playerColor: PlayerColor) : BaseCommand(playerColor) {

    @Inject
    protected lateinit var updatePlayerUsecase: UpdatePlayerUsecase

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        val playerData = getPlayerData(game, playerColor)
        val sameField = playerData.steps!!.size > 1 && playerData.previousStep.isPosition(spaceField.gamePosition)
        val hasConnection = getPlayerField(game, playerColor).hasConnectionTo(spaceField)
        return hasConnection && (sameField && playerData.phase.isMoving() || playerData.canMove())
    }

    override fun execute(game: MainGame) {
        val player = getPlayer(game, playerColor)
        val playerImage = player.playerImage
        val fieldImage = spaceField.fieldImage

        playerImage.moveToPoint(playerImage, fieldImage, playerImage.getNONEAction(playerImage, fieldImage))

        player.playerData.setSteps(player.playerData, spaceField)
        player.gamePosition.setPosition(spaceField.gamePosition)

        Logger.println("Player Field: ${spaceField.gamePosition}, ${spaceField.fieldType.name}")

        updatePlayerUsecase.execute(listOf(player.playerData))
    }

}