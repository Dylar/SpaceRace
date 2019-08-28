package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.FieldsNotConnectedException
import de.bitb.spacerace.exceptions.NoStepsLeftException
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import de.bitb.spacerace.usecase.game.check.CheckPlayerPhaseUsecase
import de.bitb.spacerace.utils.Logger
import io.reactivex.Single
import javax.inject.Inject

class MoveUsecase @Inject constructor(
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val checkPlayerPhaseUsecase: CheckPlayerPhaseUsecase,
        private val graphicController: GraphicController,
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<Pair<PlayerColor, PositionData>, Pair<PlayerColor, SpaceField>> {

    override fun buildUseCaseSingle(params: Pair<PlayerColor, SpaceField>) =
            params.let { (playerColor, target) ->
                checkCurrentPlayer(playerColor)
                        .andThen(checkMovePhase(playerColor))
                        .flatMap { checkMoveable(it, target) }
                        .flatMap { move(it, target) }
                        .doOnSuccess { (playerColor, gamePosition) ->
                            //TODO dont do this here
                            val player = graphicController.getPlayer(playerColor)
                            val playerImage = player.playerImage
                            val targetField = graphicController.getField(gamePosition)
                            val fieldImage = targetField.fieldImage

                            playerImage.moveToPoint(playerImage,
                                    fieldImage,
                                    playerImage.getNONEAction(playerImage, fieldImage))
                            player.gamePosition.setPosition(gamePosition)
                        }
            }

    private fun checkCurrentPlayer(playerColor: PlayerColor) =
            checkCurrentPlayerUsecase.buildUseCaseCompletable(playerColor)

    private fun checkMovePhase(playerColor: PlayerColor) =
            checkPlayerPhaseUsecase.buildUseCaseSingle(playerColor to Phase.MOVE)

    private fun checkMoveable(playerData: PlayerData, target: SpaceField): Single<PlayerData> =
            Single.create { emitter ->
                val color = playerData.playerColor

//                val isMovePhase = playerData.phase.isMoving()
//                if (!isMovePhase) {
//                    emitter.onError(NotMovePhaseException(color, target))
//                    return@create
//                }

                val hasConnection = graphicController
                        .getPlayerField(color)
                        .hasConnectionTo(target)
                if (!hasConnection) {
                    emitter.onError(FieldsNotConnectedException(color, target))
                    return@create
                }

                val stepsLeft = playerData.areStepsLeft()
                val isPreviousPosition = playerData.isPreviousPosition(target.gamePosition)

                if (isPreviousPosition || stepsLeft) {
                    emitter.onSuccess(playerData)
                } else emitter.onError(NoStepsLeftException(color, target))
            }

    private fun move(playerData: PlayerData,
                     targetField: SpaceField
    ): Single<Pair<PlayerColor, PositionData>> {

        playerData.setSteps(playerData, targetField)
//        playerData.positionField(fieldData) TODO make that so
//        setGraphics(playerData.playerColor, targetField.gamePosition)
        Logger.println(
                "Player: $playerData",
                "Field: ${targetField.fieldType.name}, ${targetField.id}"
        )
        return playerDataSource.insertAll(playerData)
                .andThen(Single.just(playerData.playerColor to targetField.gamePosition))
    }

}