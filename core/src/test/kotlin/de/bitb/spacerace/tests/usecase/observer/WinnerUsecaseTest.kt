package de.bitb.spacerace.tests.usecase.observer

import de.bitb.spacerace.config.BITRISE_BORG
import de.bitb.spacerace.config.DEBUG_WIN_FIELD
import de.bitb.spacerace.config.GOAL_CREDITS
import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.*
import org.junit.After
import org.junit.Test

class WinnerUsecaseTest : GameTest() {

    @After
    override fun teardown() {
        super.teardown()
        DEBUG_WIN_FIELD = true
    }

    @Test
    fun moveToAnyField_DontWinGame() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()
                    move()

                    nextPhase()
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertPlayerVictories(TEST_PLAYER_1, 0)
                    assertNotGameEnd()
                    assertNotWinner(TEST_PLAYER_1)
                    assertNotWinner(TEST_PLAYER_2)
                }
    }

    @Test
    fun goals1_moveOnGoal_WinGame() {
//        assertTrue(true) //TODO bitrise bug... maybe wait for win or whatever observer
        if (BITRISE_BORG) {
            TestEnvironment()
                    .initGame()
                    .moveToGoal()
                    .nextPhase()
                    .apply { waitForIt(100) }
                    .assertCurrentPlayer(TEST_PLAYER_1)
                    .assertPlayerVictories(TEST_PLAYER_1)
                    .assertGameEnd()
                    .assertWinner(TEST_PLAYER_1)
                    .assertNotWinner(TEST_PLAYER_2)

        }
    }

    @Test
    fun goals2_moveOnGoal_DontWinGame_checkNewGoal() {
        TestEnvironment()
                .apply {
                    DEBUG_WIN_FIELD = false
                    initGame(winAmount = 2)
                    val goalPosition = centerBottomField
                    assertGoalField(goalPosition)
                    assertPlayerVictories(TEST_PLAYER_1, 0)

                    moveToGoal()
                    nextPhase()

                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertPlayerVictories(TEST_PLAYER_1)
                    assertNotGameEnd()
                    assertNotWinner(TEST_PLAYER_1)
                    assertNotWinner(TEST_PLAYER_2)

                    assertNotGoalField(goalPosition)
                }
    }

    @Test
    fun goals2_moveOnGoal_DontWinGame_playerMoneyIncreased() {
        TestEnvironment()
                .also { DEBUG_WIN_FIELD = false }
                .initGame(winAmount = 2)
                .moveToGoal()
                .nextPhase { it.player.credits == START_CREDITS + GOAL_CREDITS }
                .assertPlayerVictories(TEST_PLAYER_1)
                .assertNotGameEnd()
    }

}