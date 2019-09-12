package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.FieldsNotConnectedException
import de.bitb.spacerace.exceptions.NoStepsLeftException
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import de.bitb.spacerace.usecase.game.check.CheckPlayerPhaseUsecase
import de.bitb.spacerace.usecase.game.getter.GetTargetableFieldUsecase
import de.bitb.spacerace.utils.Logger
import de.bitb.spacerace.utils.RXFunctions.zipParallel
import io.reactivex.Single
import javax.inject.Inject

class MoveUsecase @Inject constructor(
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val checkPlayerPhaseUsecase: CheckPlayerPhaseUsecase,
        private val getTargetableFieldUsecase: GetTargetableFieldUsecase,
        private val playerDataSource: PlayerDataSource,
        private val mapDataSource: MapDataSource
) : ResultUseCase<MoveResult, Pair<PlayerColor, PositionData>> {

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
                val playerPosition = playerData.positionField.target

                val hasConnection = playerPosition.connections.any { target.gamePosition.isPosition(it.gamePosition) }
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
    ): Single<MoveResult> {
        playerData.setSteps(targetField.gamePosition)
        playerData.positionField.target = targetField
        Logger.println(
                "Player: $playerData",
                "Field: ${targetField.fieldType.name}, ${targetField.uuid}"
        )
        val moveInfo = MoveResult(playerData, targetField.gamePosition, playerData.areStepsLeft(), playerData.previousStep)
        return playerDataSource.insert(playerData)
                .andThen(getTargetableFieldUsecase.buildUseCaseSingle(playerData))
                .map { moveInfo.apply { targetableFields.addAll(it) } }
    }

}

data class MoveResult(
        var player: PlayerData,
        var position: PositionData,
        var stepsLeft: Boolean,
        var previousPosition: PositionData,
        var phase: Phase = Phase.MOVE,
        var targetableFields: MutableList<FieldData> = mutableListOf()
)

data class ConnectionResult(
        var position: PositionData,
        var stepsLeft: Boolean,
        var previousPosition: PositionData,
        var phase: Phase,
        var targetableFields: MutableList<FieldData> = mutableListOf()
)
