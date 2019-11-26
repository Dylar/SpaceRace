package de.bitb.spacerace.tests.usecase.action

import de.bitb.spacerace.core.*
import de.bitb.spacerace.core.exceptions.MoreDiceException
import de.bitb.spacerace.core.exceptions.NotCurrentPlayerException
import de.bitb.spacerace.core.exceptions.StepsLeftException
import de.bitb.spacerace.env.*
import de.bitb.spacerace.grafik.model.enums.Phase
import org.junit.Assert.assertTrue
import org.junit.Test

class NextPhaseUsecaseTest : GameTest() {

    @Test
    fun simpleAssert() {
        val a = 1
        val b = 1
        assertTrue(a + b == 2)
    }

    @Test
    fun onlyCurrentPlayerCanChangePhase_Main1ToMove() {
        TestEnvironment()
                .apply {
                    initGame()
                    dice()

                    //assert start
                    assertNotCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN1)

                    //do next phase action with not current player
                    nextPhase(TEST_PLAYER_2, error = NotCurrentPlayerException(TEST_PLAYER_2))

                    //assert still same phase
                    assertNotCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN1)

                    nextPhase(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MOVE)
                }
    }

    @Test
    fun onlyCurrentPlayerCanChangePhase_MoveToMain2() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()
                    move()

                    //assert start
                    assertNotCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MOVE)

                    //do next phase action with not current player
                    nextPhase(TEST_PLAYER_2, error = NotCurrentPlayerException(TEST_PLAYER_2))

                    //assert still same phase
                    assertNotCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MOVE)

                    nextPhase(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN2)
                }
    }

    @Test
    fun onlyCurrentPlayerCanChangePhase_Main2ToMain1_playerChanged() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMain2Phase()

                    //assert start
                    assertNotCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN2)

                    //do next phase action with not current player
                    nextPhase(TEST_PLAYER_2, error = NotCurrentPlayerException(TEST_PLAYER_2))

                    //assert still same phase
                    assertNotCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN2)

                    nextPhase(TEST_PLAYER_1)

                    //assert player changed
                    assertNotCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPhase(Phase.MAIN1)
                }
    }

    @Test
    fun noDice_nextPhaseClicked_failure_stillMainPhase() {
        TestEnvironment()
                .apply {
                    initGame()
                    //assert start
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN1)

                    //do next phase action
                    nextPhase(error = MoreDiceException(currentPlayerColor, 0, 1))

                    //assert still same phase
                    assertCurrentPhase(Phase.MAIN1)
                }
    }

    @Test
    fun dice_nextPhaseClicked_success_movePhase() {
        TestEnvironment()
                .apply {
                    initGame()
                    //assert start
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN1)

                    //do dice action
                    dice()

                    //do next phase action
                    nextPhase()

                    //assert move phase
                    assertCurrentPhase(Phase.MOVE)
                }
    }


    @Test
    fun movePhase_nextPhaseClicked_failure_movePhase_stepsLeft() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()

                    nextPhase(error = StepsLeftException(currentPlayerColor, 1))

                    //assert move phase
                    assertCurrentPhase(Phase.MOVE)
                }
    }

    @Test
    fun movePhase_move_nextPhaseClicked_success_main2Phase() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()

                    //get move target
                    val target = leftTopField

                    //move action
                    move(target = target)
                    nextPhase()

                    assertCurrentPhase(Phase.MAIN2)
                }
    }

    @Test
    fun movePhase_move1_nextPhaseClicked_failure_movePhase_stepsLeft_move1_nextPhaseSuccess() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMovePhase(setDice = 2)

                    val target1 = leftTopField
                    val target2 = centerTopField
                    assertNotSameField(target1, target2)
                    val playerField1 = getPlayerPosition(TEST_PLAYER_1)

                    //move1
                    move(target = target1)
                    val playerField2 = getPlayerPosition(TEST_PLAYER_1)
                    assertNotSameField(playerField1, playerField2)

                    //next phase failed
                    nextPhase(error = StepsLeftException(currentPlayerColor, 1))
                    assertCurrentPhase(Phase.MOVE)

                    //move2
                    move(target = target2)

                    //next phase success
                    nextPhase()

                    assertCurrentPhase(Phase.MAIN2)
                }
    }

    @Test
    fun movePhase_move2_nextPhaseClicked_success_main2Phase() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMovePhase(setDice = 2)

                    //move action
                    val target1 = leftTopField
                    val target2 = centerTopField
                    assertNotSameField(target1, target2)

                    move(target = target1)
                    assertPlayerOnField(TEST_PLAYER_1, target1)
                    move(target = target2)

                    nextPhase()

                    //assert main2 phase
                    assertCurrentPhase(Phase.MAIN2)
                }
    }

    @Test
    fun main2Phase_nextPhaseClicked_success_currentPlayerChanged() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMain2Phase()

                    assertCurrentPlayer(TEST_PLAYER_1)

                    nextPhase()

                    //assert main1 phase
                    assertCurrentPhase(Phase.MAIN1)
                    assertCurrentPlayer(TEST_PLAYER_2)
                }
    }

    @Test
    fun changePlayer1_toPlayer2() {
        TestEnvironment()
                .apply {
                    initGame()

                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN1)

                    changePlayerTo(TEST_PLAYER_2)
                    assertCurrentPlayer(TEST_PLAYER_2)
                    assertCurrentPhase(Phase.MAIN1)
                }
    }

    @Test
    fun changePlayer1_skipPlayer2_toPlayer3() {
        TestEnvironment()
                .apply {
                    initGame(TEST_PLAYER_1, TEST_PLAYER_2, TEST_PLAYER_3)

                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN1)

                    changePlayerTo(TEST_PLAYER_3)
                    assertCurrentPlayer(TEST_PLAYER_3)
                    assertCurrentPhase(Phase.MAIN1)
                }
    }

    @Test
    fun threePlayer_changePlayer1_toPlayer2_endRound_player1EqualsCurrent() {
        TestEnvironment()
                .initGame(TEST_PLAYER_1, TEST_PLAYER_2, TEST_PLAYER_3)
                .assertCurrentPlayer(TEST_PLAYER_1)
                .assertCurrentPhase(Phase.MAIN1)

                .changePlayerTo(TEST_PLAYER_2)
                .assertCurrentPlayer(TEST_PLAYER_2)
                .assertCurrentPhase(Phase.MAIN1)

                .endRound()
                .assertCurrentPlayer(TEST_PLAYER_1)
                .assertCurrentPhase(Phase.MAIN1)
    }

}