package de.bitb.spacerace.core

import de.bitb.spacerace.controller.toConnectionResult
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.usecase.game.action.ConnectionResult
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

fun SpaceEnvironment.assertDBMap(assertField: (MapData) -> Boolean) {
    getMapUsecase.buildUseCaseSingle()
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
        connection: SpaceConnection = createConnection(currentPosition, leftTopField),
        isConnected: Boolean = false,
        assertSuccess: (MoveResult) -> Boolean = { checkConnection(connection, it.toConnectionResult(), isConnected) }
) = move(
        player = player,
        target = connection.spaceField2.gamePosition,
        assertSuccess = assertSuccess
)

fun SpaceEnvironment.checkConnection(
        connection: SpaceConnection,
        connectionResult: ConnectionResult,
        isConnected: Boolean = false
) = isConnected == fieldController.connectionCanBeCrossed(connection, connectionResult)

fun SpaceEnvironment.assertConnection(
        connection: SpaceConnection,
        connectionResult: ConnectionResult,
        isConnected: Boolean = false
) = assertEquals(isConnected, fieldController.connectionCanBeCrossed(connection, connectionResult))