package de.bitb.spacerace.core

import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.action.MoveResult
import io.reactivex.Single
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*

fun SpaceEnvironment.assertDBPlayer(player: PlayerColor, assertPlayer: (PlayerData) -> Boolean) {
    getPlayerUsecase.buildUseCaseSingle(player)
            .assertValue(assertPlayer)
}

fun SpaceEnvironment.assertDBField(gamePosition: PositionData, assertField: (FieldData) -> Boolean) {
    getFieldUsecase.buildUseCaseSingle(gamePosition)
            .assertValue(assertField)
}

fun SpaceEnvironment.assertDBMap(assertField: (SaveData) -> Boolean) {
    getSaveGameUsecase.buildUseCaseSingle()
            .assertValue(assertField)
}

fun SpaceEnvironment.assertTargetField(playerColor: PlayerColor, assertField: (List<FieldData>) -> Boolean) {
    getPlayerUsecase
            .buildUseCaseSingle(playerColor)
            .flatMap { player ->
                getTargetableFieldUsecase.buildUseCaseSingle(player)
            }
            .assertValue(assertField)
}

private fun <T> Single<T>.assertValue(assertIt: (T) -> Boolean) {
    test().await()
            .assertComplete()
            .assertValue { assertIt(it) }
}

fun SpaceEnvironment.assertCurrentPlayer(testPlayer: PlayerColor) =
        assertThat(currentPlayerColor, `is`(testPlayer))

fun SpaceEnvironment.assertNotCurrentPlayer(testPlayer: PlayerColor) =
        assertThat(currentPlayerColor, `is`(not(testPlayer)))

fun SpaceEnvironment.assertCurrentPhase(phase: Phase) =
        assertDBPlayer(currentPlayerColor) { it.phase == phase }

fun SpaceEnvironment.assertDiceResult(
        diceResult: Int,
        player: PlayerColor = currentPlayerColor
) = assertDBPlayer(player) { it.stepsLeft() == diceResult }

fun SpaceEnvironment.assertSameField(field1: PositionData, field2: PositionData) =
        assertTrue(field1.isPosition(field2))

fun SpaceEnvironment.assertNotSameField(field1: PositionData, field2: PositionData) =
        assertFalse(field1.isPosition(field2))

fun SpaceEnvironment.assertGoalField(positionData: PositionData) =
        assertDBMap { it.goal.target.gamePosition.isPosition(positionData) }

fun SpaceEnvironment.assertNotGoalField(positionData: PositionData) =
        assertDBMap { !it.goal.target.gamePosition.isPosition(positionData) }

fun SpaceEnvironment.assertPlayerOnField(player: PlayerColor, field: PositionData) =
        assertDBPlayer(player) { it.gamePosition.isPosition(field) }

fun SpaceEnvironment.assertPlayerNotOnField(player: PlayerColor, fieldPosition: PositionData) =
        assertDBPlayer(player) { !it.positionField.target.gamePosition.isPosition(fieldPosition) }

fun SpaceEnvironment.assertPlayerVictories(player: PlayerColor, amount: Long = 1) =
        assertDBPlayer(player) { it.victories == amount }

fun SpaceEnvironment.assertWinner(player: PlayerColor) {
    val winner = winnerObserver.values().lastOrNull() ?: NONE_PLAYER_DATA
    assertThat(winner.playerColor, `is`(player))
}

fun SpaceEnvironment.assertNotWinner(player: PlayerColor) {
    val winner = winnerObserver.values().lastOrNull() ?: NONE_PLAYER_DATA
    assertThat(winner.playerColor, `is`(not(player)))
}

fun SpaceEnvironment.assertGameEnd() {
    val winner = winnerObserver.values()
    assertThat(winner, `is`(notNullValue()))
}

fun SpaceEnvironment.assertNotGameEnd() {
    val winner = winnerObserver.values().lastOrNull()
    assertThat(winner, `is`(nullValue()))
}

fun SpaceEnvironment.assertConnectionAfterMove(
        player: PlayerColor = currentPlayerColor,
        connection: List<PositionData> = listOf(currentPosition, leftTopField),
        isConnected: Boolean = false,
        assertSuccess: (MoveResult) -> Boolean = {  checkConnection(player, it.targetableFields, connection, isConnected) }
) = move(
        player = player,
        target = connection[0],
        assertSuccess = assertSuccess
)

fun SpaceEnvironment.assertConnection(
        playerColor: PlayerColor = currentPlayerColor,
        connection: List<PositionData>,
        isConnected: Boolean = false
) =
        assertTargetField(playerColor) { fields ->
            checkConnection(playerColor, fields, connection, isConnected)
        }

fun SpaceEnvironment.checkConnection(
        playerColor: PlayerColor = currentPlayerColor,
        fields: List<FieldData>,
        connection: List<PositionData>,
        isConnected: Boolean = false
) =
        getDBPlayer(playerColor).gamePosition
                .let { playerPosition ->
                    val isPlayerField = connection.any { it.isPosition(playerPosition) }

                    fun checkIt(fieldPosition: PositionData) =
                            isPlayerField && connection.any { it.isPosition(fieldPosition) }

                    if (isConnected) fields.any { checkIt(it.gamePosition) }
                    else fields.none { checkIt(it.gamePosition) }
                }


