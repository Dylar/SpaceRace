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

                    nextPhase()
                    assertCurrentPlayer( TEST_PLAYER_1)
                    assertPlayerVictories( TEST_PLAYER_1, 0)
                    assertNotGameEnd()
                    assertNotWinner( TEST_PLAYER_1)
                    assertNotWinner( TEST_PLAYER_2)
                }
    }

    @Test
    fun goals1_moveOnGoal_WinGame() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    moveToGoal()

                    nextPhase()
                    assertCurrentPlayer( TEST_PLAYER_1)
                    assertPlayerVictories( TEST_PLAYER_1)
                    assertGameEnd()
                    assertWinner( TEST_PLAYER_1)
                    assertNotWinner( TEST_PLAYER_2)
                }
    }

    @Test
    fun goals2_moveOnGoal_DontWinGame() {
        SpaceEnvironment()
                .apply {
                    initGame(winAmount = 2)
                    moveToGoal()

                    nextPhase()
                    assertCurrentPlayer( TEST_PLAYER_1)
                    assertPlayerVictories( TEST_PLAYER_1)
                    assertNotGameEnd()
                    assertNotWinner( TEST_PLAYER_1)
                    assertNotWinner( TEST_PLAYER_2)
                }
    }

}