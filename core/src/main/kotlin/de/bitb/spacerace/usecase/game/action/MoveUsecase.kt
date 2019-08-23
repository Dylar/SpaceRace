package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.utils.Logger
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.objecthandling.DEFAULT
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.usecase.ExecuteUseCase
import de.bitb.spacerace.usecase.game.GetPlayerUsecase
import io.reactivex.Completable
import javax.inject.Inject

class MoveUsecase @Inject constructor(
        private val getPlayerUsecase: GetPlayerUsecase,
        private val playerController: PlayerController,
        private val fieldController: FieldController,
        private val playerDataSource: PlayerDataSource
) : ExecuteUseCase<Pair<PlayerColor, SpaceField>>,
        DefaultFunction by DEFAULT {

    override fun buildUseCaseCompletable(params: Pair<PlayerColor, SpaceField>) =
            params.let { (playerColor, target) ->
                getPlayerUsecase.buildUseCaseSingle(playerColor)
                        .flatMapCompletable { move(it, target) }
            }

    private fun move(playerData: PlayerData, targetField: SpaceField): Completable {
        if (canExecute(playerData, targetField)) {
            val player = getPlayer(playerController, playerData.playerColor)
            val playerImage = player.playerImage
            val fieldImage = targetField.fieldImage

            //TODO dont do this here
            playerImage.moveToPoint(playerImage, fieldImage, playerImage.getNONEAction(playerImage, fieldImage))

            playerData.setSteps(playerData, targetField)
            player.gamePosition.setPosition(targetField.gamePosition)
        }

        Logger.println(
                "Player: $playerData",
                "Field: ${targetField.fieldType.name}, ${targetField.id}"
        )

        return playerDataSource.insertAll(playerData)
    }

    private fun canExecute(playerData: PlayerData, spaceField: SpaceField): Boolean {
        val sameField = playerData.steps.size > 1 && playerData.previousStep.isPosition(spaceField.gamePosition)
        val hasConnection = getPlayerField(playerController, fieldController, playerData.playerColor).hasConnectionTo(spaceField)
        return hasConnection && (sameField && playerData.phase.isMoving() || playerController.canMove(playerData))
    }

}