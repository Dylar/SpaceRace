package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.Logger
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.usecase.game.UpdatePlayerUsecase
import javax.inject.Inject

class MoveCommand(val spaceField: SpaceField, playerData: PlayerData) : BaseCommand(playerData) {

    @Inject
    protected lateinit var updatePlayerUsecase: UpdatePlayerUsecase

    @Inject
    protected lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(game: MainGame): Boolean {
        val sameField = playerData.steps.size > 1 && playerData.previousStep.isPosition(spaceField.gamePosition)
        val hasConnection = getPlayerField(game, playerData.playerColor).hasConnectionTo(spaceField)
        return hasConnection && (sameField && playerData.phase.isMoving() || playerController.canMove(playerData))
    }

    override fun execute(game: MainGame) {
        val player = getPlayer(game, playerData.playerColor)
        val playerImage = player.playerImage
        val fieldImage = spaceField.fieldImage

        playerImage.moveToPoint(playerImage, fieldImage, playerImage.getNONEAction(playerImage, fieldImage))

        playerData.setSteps(playerData, spaceField)
        player.gamePosition.setPosition(spaceField.gamePosition)

        Logger.println("Player Field: ${spaceField.gamePosition}, ${spaceField.fieldType.name}")

        updatePlayerUsecase.execute(listOf(playerData))
    }

}