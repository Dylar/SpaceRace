package de.bitb.spacerace.core

import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.env.TestEnvironment
import de.bitb.spacerace.env.move
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.action.MoveResult
import io.reactivex.Single
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*

fun TestEnvironment.assertDBPlayer(player: PlayerColor, assertPlayer: (PlayerData) -> Boolean) {
    getPlayerUsecase.buildUseCaseSingle(player)
            .assertValue(assertPlayer)
}

fun TestEnvironment.assertDBMap(assertField: (SaveData) -> Boolean) {
    getSaveGameUsecase.buildUseCaseSingle()
            .assertValue(assertField)
}

fun TestEnvironment.assertTargetField(playerColor: PlayerColor, assertField: (List<FieldData>) -> Boolean) {
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

fun TestEnvironment.assertCurrentPlayer(testPlayer: PlayerColor) =
        assertThat(currentPlayerColor, `is`(testPlayer))

fun TestEnvironment.assertNotCurrentPlayer(testPlayer: PlayerColor) =
        assertThat(currentPlayerColor, `is`(not(testPlayer)))

fun TestEnvironment.assertCurrentPhase(phase: Phase) =
        assertDBPlayer(currentPlayerColor) { it.phase == phase }

fun TestEnvironment.assertDiceResult(
        diceResult: Int,
        player: PlayerColor = currentPlayerColor
) = assertDBPlayer(player) { it.stepsLeft() == diceResult }

fun TestEnvironment.assertSameField(field1: PositionData, field2: PositionData) =
        assertTrue(field1.isPosition(field2))

fun TestEnvironment.assertNotSameField(field1: PositionData, field2: PositionData) =
        assertFalse(field1.isPosition(field2))

fun TestEnvironment.assertGoalField(positionData: PositionData) =
        assertDBMap { it.goal.target.gamePosition.isPosition(positionData) }

fun TestEnvironment.assertNotGoalField(positionData: PositionData) =
        assertDBMap { !it.goal.target.gamePosition.isPosition(positionData) }

fun TestEnvironment.assertPlayerOnField(player: PlayerColor, field: PositionData) =
        assertDBPlayer(player) { it.gamePosition.isPosition(field) }

fun TestEnvironment.assertPlayerNotOnField(player: PlayerColor, fieldPosition: PositionData) =
        assertDBPlayer(player) { !it.positionField.target.gamePosition.isPosition(fieldPosition) }

fun TestEnvironment.assertPlayerVictories(player: PlayerColor, amount: Long = 1) =
        assertDBPlayer(player) { it.victories == amount }

fun TestEnvironment.assertWinner(player: PlayerColor) {
    val winner = winnerObserver.values().lastOrNull() ?: NONE_PLAYER_DATA
    assertThat(winner.playerColor, `is`(player))
}

fun TestEnvironment.assertNotWinner(player: PlayerColor) {
    val winner = winnerObserver.values().lastOrNull() ?: NONE_PLAYER_DATA
    assertThat(winner.playerColor, `is`(not(player)))
}

fun TestEnvironment.assertGameEnd() {
    val winner = winnerObserver.values()
    assertThat(winner, `is`(notNullValue()))
}

fun TestEnvironment.assertNotGameEnd() {
    val winner = winnerObserver.values().lastOrNull()
    assertThat(winner, `is`(nullValue()))
}

fun TestEnvironment.assertConnectionAfterMove(
        player: PlayerColor = currentPlayerColor,
        connection: List<PositionData> = listOf(currentPosition, leftTopField),
        isConnected: Boolean = false,
        assertSuccess: (MoveResult) -> Boolean = {  checkConnection(player, it.targetableFields, connection, isConnected) }
) = move(
        player = player,
        target = connection[0],
        assertSuccess = assertSuccess
)

fun TestEnvironment.assertConnection(
        playerColor: PlayerColor = currentPlayerColor,
        connection: List<PositionData>,
        isConnected: Boolean = false
) =
        assertTargetField(playerColor) { fields ->
            checkConnection(playerColor, fields, connection, isConnected)
        }

fun TestEnvironment.checkConnection(
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


