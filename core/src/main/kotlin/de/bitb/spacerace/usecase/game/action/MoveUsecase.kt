package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.MoveInfo
import de.bitb.spacerace.controller.toConnectionInfo
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.FieldsNotConnectedException
import de.bitb.spacerace.exceptions.NoStepsLeftException
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.getPlayerPosition
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import de.bitb.spacerace.usecase.game.check.CheckPlayerPhaseUsecase
import de.bitb.spacerace.utils.Logger
import de.bitb.spacerace.utils.RXFunctions.zipParallel
import io.reactivex.Single
import javax.inject.Inject

class MoveUsecase @Inject constructor(
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val checkPlayerPhaseUsecase: CheckPlayerPhaseUsecase,
        private val playerDataSource: PlayerDataSource,
        private val mapDataSource: MapDataSource,
        private val fieldController: FieldController,
        private val graphicController: GraphicController
) : ResultUseCase<MoveInfo, Pair<PlayerColor, PositionData>> {

    override fun buildUseCaseSingle(params: Pair<PlayerColor, PositionData>) =
            params.let { (playerColor, target) ->
                checkCurrentPlayer(playerColor)
                        .andThen(zipParallel(
                                checkMovePhase(playerColor),
                                getField(target)
                        ) { playerData, fieldData ->
                            playerData to fieldData
                        })
                        .flatMap { (playerData, fieldData) -> checkMoveable(playerData, fieldData) }
                        .flatMap { (playerData, fieldData) -> move(playerData, fieldData) }
                        .doOnSuccess {
                            graphicController.getPlayerPosition(it.playerColor)
                                    .setPosition(it.position) //TODO put position in playerdata

                            fieldController.setConnectionColor(it.toConnectionInfo())
                        }
            }

    private fun getField(positionData: PositionData): Single<FieldData> =
            mapDataSource.getField(positionData)

    private fun checkCurrentPlayer(playerColor: PlayerColor) =
            checkCurrentPlayerUsecase.buildUseCaseCompletable(playerColor)

    private fun checkMovePhase(playerColor: PlayerColor) =
            checkPlayerPhaseUsecase.buildUseCaseSingle(playerColor to Phase.MOVE)

    private fun checkMoveable(playerData: PlayerData, target: FieldData): Single<Pair<PlayerData, FieldData>> =
            Single.create { emitter ->
                val color = playerData.playerColor

                val hasConnection = graphicController
                        .getPlayerField(color)
                        .hasConnectionTo(target.gamePosition)
                if (!hasConnection) {
                    emitter.onError(FieldsNotConnectedException(color, target))
                    return@create
                }

                val stepsLeft = playerData.areStepsLeft()
                val isPreviousPosition = playerData.isPreviousPosition(target.gamePosition)

                if (isPreviousPosition || stepsLeft) {
                    emitter.onSuccess(playerData to target)
                } else emitter.onError(NoStepsLeftException(color, target))
            }

    private fun move(
            playerData: PlayerData,
            targetField: FieldData
    ): Single<MoveInfo> {
        playerData.setSteps(targetField.gamePosition)
        playerData.positionField.target = targetField
        Logger.println(
                "Player: $playerData",
                "Field: ${targetField.fieldType.name}, ${targetField.uuid}"
        )
        val moveInfo = MoveInfo(playerData.playerColor, targetField.gamePosition, playerData.areStepsLeft(), playerData.previousStep)
        return playerDataSource.insert(playerData)
                .andThen(Single.just(moveInfo))
    }

}