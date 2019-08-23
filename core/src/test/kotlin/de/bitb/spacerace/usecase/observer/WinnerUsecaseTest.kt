package de.bitb.spacerace.usecase.observer

import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_1
import de.bitb.spacerace.env.TEST_PLAYER_2
import org.junit.Test

class WinnerUsecaseTest : GameTest() {

    @Test
    fun moveToAnyField_DontWinGame() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()
                    move()
                }
                .also { env ->
                    env.nextPhase()
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertPlayerVictories(env, TEST_PLAYER_1, 0)
                    assertNotGameEnd(env)
                    assertNotWinner(env, TEST_PLAYER_1)
                    assertNotWinner(env, TEST_PLAYER_2)
                }
    }

    @Test
    fun goals1_moveOnGoal_WinGame() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    moveToGoal()
                }
                .also { env ->
                    env.nextPhase()
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertPlayerVictories(env, TEST_PLAYER_1)
                    assertGameEnd(env)
                    assertWinner(env, TEST_PLAYER_1)
                    assertNotWinner(env, TEST_PLAYER_2)
                }
    }

    @Test
    fun goals2_moveOnGoal_DontWinGame() {
        SpaceEnvironment()
                .apply {
                    initGame(winAmount = 2)
                    moveToGoal()
                }
                .also { env ->
                    env.nextPhase()
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertPlayerVictories(env, TEST_PLAYER_1)
                    assertNotGameEnd(env)
                    assertNotWinner(env, TEST_PLAYER_1)
                    assertNotWinner(env, TEST_PLAYER_2)
                }
    }

}