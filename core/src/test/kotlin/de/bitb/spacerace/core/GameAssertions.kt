package de.bitb.spacerace.core

import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.database.items.getModifierValues
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.env.TestEnvironment
import de.bitb.spacerace.env.move
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.action.MoveResult
import io.reactivex.Single
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*

fun TestEnvironment.assertDBPlayer(player: PlayerColor, assertPlayer: (PlayerData) -> Boolean) = this.apply {
    getPlayerUsecase.buildUseCaseSingle(player)
            .assertValue(assertPlayer)
}

fun TestEnvironment.assertDBSaveData(assertField: (SaveData) -> Boolean) = this.apply {
    getSaveGameUsecase.buildUseCaseSingle()
            .assertValue(assertField)
}

fun TestEnvironment.assertTargetField(playerColor: PlayerColor, assertField: (List<FieldData>) -> Boolean) = this.apply {
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

fun TestEnvironment.assertCurrentPlayer(testPlayer: PlayerColor) = this.apply {
    assertThat(currentPlayerColor, `is`(testPlayer))
}

fun TestEnvironment.assertNotCurrentPlayer(testPlayer: PlayerColor) = this.apply {
    assertThat(currentPlayerColor, `is`(not(testPlayer)))
}

fun TestEnvironment.assertCurrentPhase(phase: Phase) = this.apply {
    assertDBPlayer(currentPlayerColor) { it.phase == phase }
}

fun TestEnvironment.assertCredits(
        player: PlayerColor = currentPlayerColor,
        credits: Int = START_CREDITS
) = this.apply {
    assertDBPlayer(player) { it.credits == credits }
}

fun TestEnvironment.assertCreditsNot(
        player: PlayerColor = currentPlayerColor,
        credits: Int = 0
) = this.apply {
    assertDBPlayer(player) {
        assertThat(it.mines.size, `is`(1))
        assertThat(it.credits, `is`(not(credits)))
        it.credits != credits
    }
}

fun TestEnvironment.assertPlayerModi(assertMod: Double = 0.0, assertAdd: Int = 0) =
        this.apply {
            val player = getDBPlayer(currentPlayerColor)
            val (modifierValue, addValue) = player.getModifierValues()
            assertEquals(assertMod, modifierValue, 0.0)
            assertEquals(assertAdd, addValue)
        }

fun TestEnvironment.assertDiceResult(
        diceResult: Int,
        player: PlayerColor = currentPlayerColor
) = this.apply { assertDBPlayer(player) { it.stepsLeft() == diceResult } }

fun TestEnvironment.assertSameField(field1: PositionData, field2: PositionData) = this.apply {
    assertTrue(field1.isPosition(field2))
}

fun TestEnvironment.assertNotSameField(field1: PositionData, field2: PositionData) = this.apply {
    assertFalse(field1.isPosition(field2))
}

fun TestEnvironment.assertGoalField(positionData: PositionData) = this.apply {
    assertDBSaveData { it.goal.target.gamePosition.isPosition(positionData) }
}

fun TestEnvironment.assertNotGoalField(positionData: PositionData) = this.apply {
    assertDBSaveData { !it.goal.target.gamePosition.isPosition(positionData) }
}

fun TestEnvironment.assertPlayerOnField(player: PlayerColor, field: PositionData) = this.apply {
    assertDBPlayer(player) { it.gamePosition.isPosition(field) }
}

fun TestEnvironment.assertPlayerNotOnField(player: PlayerColor, fieldPosition: PositionData) = this.apply {
    assertDBPlayer(player) { !it.positionField.target.gamePosition.isPosition(fieldPosition) }
}

fun TestEnvironment.assertPlayerVictories(player: PlayerColor, amount: Long = 1) = this.apply {
    assertDBPlayer(player) { it.victories == amount }
}

fun TestEnvironment.assertRoundCount(
        roundCount: Int = 1
) = this.apply { assertDBSaveData { roundCount == it.roundCount } }

fun TestEnvironment.assertWinner(player: PlayerColor) = this.apply {
    val winner = winnerObserver.values().lastOrNull() ?: NONE_PLAYER_DATA
    assertThat(winner.playerColor, `is`(player))
}

fun TestEnvironment.assertNotWinner(player: PlayerColor) = this.apply {
    val winner = winnerObserver.values().lastOrNull() ?: NONE_PLAYER_DATA
    assertThat(winner.playerColor, `is`(not(player)))
}

fun TestEnvironment.assertGameEnd() = this.apply {
    val winner = winnerObserver.values()
    assertThat(winner, `is`(notNullValue()))
}

fun TestEnvironment.assertNotGameEnd() = this.apply {
    val winner = winnerObserver.values().lastOrNull()
    assertThat(winner, `is`(nullValue()))
}

fun TestEnvironment.assertConnectionAfterMove(
        player: PlayerColor = currentPlayerColor,
        connection: List<PositionData> = listOf(currentPosition, leftTopField),
        isConnected: Boolean = false,
        assertSuccess: (MoveResult) -> Boolean = { checkConnection(player, it.targetableFields, connection, isConnected) }
) = move(
        player = player,
        target = connection[0],
        assertSuccess = assertSuccess
)

fun TestEnvironment.assertConnection(
        playerColor: PlayerColor = currentPlayerColor,
        connection: List<PositionData>,
        isConnected: Boolean = false
) = this.apply {
    assertTargetField(playerColor) { fields ->
        checkConnection(playerColor, fields, connection, isConnected)
    }
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


