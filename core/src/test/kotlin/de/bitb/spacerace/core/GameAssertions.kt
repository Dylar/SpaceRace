package de.bitb.spacerace.core

import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat


fun SpaceEnvironment.assertCurrentPlayer(testPlayer: PlayerColor) =
        assertThat(currentPlayerColor, `is`(testPlayer))

fun SpaceEnvironment.assertNotCurrentPlayer(testPlayer: PlayerColor) =
        assertThat(currentPlayerColor, `is`(not(testPlayer)))

fun SpaceEnvironment.assertCurrentPhase(phase: Phase) =
        assertThat(currentPhase, `is`(phase))

fun SpaceEnvironment.assertDiceResult(
        diceResult: Int,
        player: PlayerColor = currentPlayerColor) =
        getDBPlayer(player) { it.stepsLeft() == diceResult }

fun SpaceEnvironment.assertSameField(field1: SpaceField, field2: SpaceField) =
        assertThat(field1, `is`(field2))

fun SpaceEnvironment.assertNotSameField(field1: SpaceField, field2: SpaceField) =
        assertThat(field1, `is`(not(field2)))

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