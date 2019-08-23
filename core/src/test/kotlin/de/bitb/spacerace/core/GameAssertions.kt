package de.bitb.spacerace.core

import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat


fun assertCurrentPlayer(env: SpaceEnvironment, testPlayer: PlayerColor) =
        assertThat(env.currentPlayerColor, `is`(testPlayer))

fun assertNotCurrentPlayer(env: SpaceEnvironment, testPlayer: PlayerColor) =
        assertThat(env.currentPlayerColor, `is`(not(testPlayer)))

fun assertCurrentPhase(env: SpaceEnvironment, phase: Phase) =
        assertThat(env.currentPhase, `is`(phase))

fun assertDiceResult(env: SpaceEnvironment,
                     diceResult: Int,
                     player: PlayerColor = env.currentPlayerColor) =
        env.getPlayerUsecase
                .buildUseCaseSingle(player)
                .test()
                .await()
                .assertComplete()
                .assertValue { env.playerController.stepsLeft(it) == diceResult }

fun assertSameField(field1: SpaceField, field2: SpaceField) =
        assertThat(field1, `is`(field2))

fun assertNotSameField(field1: SpaceField, field2: SpaceField) =
        assertThat(field1, `is`(not(field2)))

fun assertPlayerOnField(env: SpaceEnvironment, player: PlayerColor, field: SpaceField) =
        assertThat(env.getPlayerField(player), `is`(field))

fun assertPlayerNotOnField(env: SpaceEnvironment, player: PlayerColor, field: SpaceField) =
        assertThat(env.getPlayerField(player), `is`(not(field)))

fun assertPlayerVictories(env: SpaceEnvironment, player: PlayerColor, amount: Long = 1) =
        env.getPlayerUsecase
                .buildUseCaseSingle(player)
                .test()
                .await()
                .assertComplete()
                .assertValue { it.victories == amount }

fun assertWinner(env: SpaceEnvironment, player: PlayerColor) {
    val winner = env.winnerObserver.values().lastOrNull() ?: NONE_PLAYER_DATA
    assertThat(winner.playerColor, `is`(player))
}

fun assertNotWinner(env: SpaceEnvironment, player: PlayerColor) {
    val winner = env.winnerObserver.values().lastOrNull() ?: NONE_PLAYER_DATA
    assertThat(winner.playerColor, `is`(not(player)))
}

fun assertGameEnd(env: SpaceEnvironment) {
    val winner = env.winnerObserver.values()
    assertThat(winner, `is`(notNullValue()))
}

fun assertNotGameEnd(env: SpaceEnvironment) {
    val winner = env.winnerObserver.values().lastOrNull()
    assertThat(winner, `is`(nullValue()))
}