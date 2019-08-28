package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.NotMovableException
import de.bitb.spacerace.model.objecthandling.getPlayerField
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
import de.bitb.spacerace.utils.Logger
import io.reactivex.Single
import javax.inject.Inject

class MoveUsecase @Inject constructor(
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val getPlayerUsecase: GetPlayerUsecase,
        private val playerController: PlayerController,
        private val graphicController: GraphicController,
        private val fieldController: FieldController,
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<Pair<PlayerData, SpaceField>, Pair<PlayerColor, SpaceField>> {

    override fun buildUseCaseSingle(params: Pair<PlayerColor, SpaceField>): Single<Pair<PlayerData, SpaceField>> =
            params.let { (playerColor, target) ->
                checkCurrentPlayerUsecase.buildUseCaseCompletable(playerColor)
                        .andThen(getPlayerUsecase.buildUseCaseSingle(playerColor))
                        .flatMap { checkMoveable(it, target) }
                        .flatMap { move(it, target) }
            }

    private fun checkMoveable(playerData: PlayerData, spaceField: SpaceField): Single<PlayerData> =
            Single.create<PlayerData> {
                val sameField = playerData.steps.size > 1 && playerData.previousStep.isPosition(spaceField.gamePosition)
                val hasConnection = graphicController.getPlayerField(fieldController, playerData.playerColor).hasConnectionTo(spaceField)
                val canMove = hasConnection && (sameField && playerData.phase.isMoving() || playerController.canMove(playerData))

                if (canMove) it.onSuccess(playerData)
                else it.onError(NotMovableException(playerData.playerColor, spaceField))
            }

    private fun move(playerData: PlayerData, targetField: SpaceField): Single<Pair<PlayerData, SpaceField>> {

        playerData.setSteps(playerData, targetField)

        Logger.println(
                "Player: $playerData",
                "Field: ${targetField.fieldType.name}, ${targetField.id}"
        )

        //TODO dont do this here
        val player = graphicController.getPlayer(playerData.playerColor)
        val playerImage = player.playerImage
        val fieldImage = targetField.fieldImage

        playerImage.moveToPoint(playerImage, fieldImage, playerImage.getNONEAction(playerImage, fieldImage))
        player.gamePosition.setPosition(targetField.gamePosition)

        return playerDataSource.insertAll(playerData).andThen(Single.just(playerData to targetField))
//        } else Single.create<Pair<PlayerData, SpaceField>> {
//            it.onError()
//        }
    }


}