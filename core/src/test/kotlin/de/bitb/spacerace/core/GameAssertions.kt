package de.bitb.spacerace.core

import de.bitb.spacerace.controller.ConnectionInfo
import de.bitb.spacerace.controller.MoveInfo
import de.bitb.spacerace.controller.toConnectionInfo
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.model.space.fields.SpaceField
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*

fun SpaceEnvironment.assertCurrentPlayer(testPlayer: PlayerColor) =
        assertThat(currentPlayerColor, `is`(testPlayer))

fun SpaceEnvironment.assertNotCurrentPlayer(testPlayer: PlayerColor) =
        assertThat(currentPlayerColor, `is`(not(testPlayer)))

fun SpaceEnvironment.assertCurrentPhase(phase: Phase) =
        getDBPlayer(currentPlayerColor){
            it.phase == phase
        }

fun SpaceEnvironment.assertDiceResult(
        diceResult: Int,
        player: PlayerColor = currentPlayerColor
) = getDBPlayer(player) { it.stepsLeft() == diceResult }

fun SpaceEnvironment.assertSameField(field1: SpaceField, field2: SpaceField) =
        assertTrue(field1.gamePosition.isPosition(field2.gamePosition))

@Deprecated("")
fun SpaceEnvironment.assertNotSameField(field1: SpaceField, field2: SpaceField) =
        assertFalse(field1.gamePosition.isPosition(field2.gamePosition))

fun SpaceEnvironment.assertOnMap(assertMap: (MapData) -> Boolean) =
        getDBMap(assertMap)

fun SpaceEnvironment.assertGoalField(positionData: PositionData) =
        assertOnMap { it.goal.target.gamePosition.isPosition(positionData) }

fun SpaceEnvironment.assertNotGoalField(positionData: PositionData) =
        assertOnMap { !it.goal.target.gamePosition.isPosition(positionData) }

fun SpaceEnvironment.assertPlayerOnField(player: PlayerColor, field: SpaceField) =
        assertThat(getPlayerField(player), `is`(field))

fun SpaceEnvironment.assertPlayerNotOnField(player: PlayerColor, field: SpaceField) =
        assertThat(getPlayerField(player), `is`(not(field)))

fun SpaceEnvironment.assertPlayerVictories(player: PlayerColor, amount: Long = 1) =
        getDBPlayer(player) {
            it.victories == amount
        }

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
        connection: SpaceConnection = SpaceConnection(currentPosition, leftTopField),
        isConnected: Boolean = false,
        assertSuccess: (MoveInfo) -> Boolean = { checkConnection(connection, it.toConnectionInfo(), isConnected) }
) = move(
        player = player,
        target = connection.spaceField2,
        assertSuccess = assertSuccess
)

fun SpaceEnvironment.checkConnection(
        connection: SpaceConnection,
        connectionInfo: ConnectionInfo,
        isConnected: Boolean = false
) = isConnected == fieldController.connectionCanBeCrossed(connection, connectionInfo)

fun SpaceEnvironment.assertConnection(
        connection: SpaceConnection,
        connectionInfo: ConnectionInfo,
        isConnected: Boolean = false
) = assertEquals(isConnected, fieldController.connectionCanBeCrossed(connection, connectionInfo))