package de.bitb.spacerace.usecase.action

import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_1
import de.bitb.spacerace.env.TEST_PLAYER_2
import de.bitb.spacerace.env.TEST_PLAYER_3
import de.bitb.spacerace.exceptions.DiceFirstException
import de.bitb.spacerace.exceptions.StepsLeftException
import de.bitb.spacerace.exceptions.NotCurrentPlayerException
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.space.fields.SpaceField
import junit.framework.Assert.assertTrue
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
        SpaceEnvironment()
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
        SpaceEnvironment()
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
        SpaceEnvironment()
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
        SpaceEnvironment()
                .apply {
                    initGame()
                    //assert start
                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN1)

                    //do next phase action
                    nextPhase(error = DiceFirstException(currentPlayerColor))

                    //assert still same phase
                    assertCurrentPhase(Phase.MAIN1)
                }
    }

    @Test
    fun dice_nextPhaseClicked_success_movePhase() {
        SpaceEnvironment()
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
        SpaceEnvironment()
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
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()

                    //get move target
                    val target: SpaceField = getRandomConnectedField()

                    //move action
                    move(target = target)
                    nextPhase()

                    assertCurrentPhase(Phase.MAIN2)
                }
    }

    @Test
    fun movePhase_move1_nextPhaseClicked_failure_movePhase_stepsLeft_move1_nextPhaseSuccess() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase(-2)

                    val target1: SpaceField = leftTopField
                    val target2: SpaceField = centerTopField
                    assertNotSameField(target1, target2)
                    val playerField1: SpaceField = getPlayerField(TEST_PLAYER_1)

                    //move1
                    move(target = target1)
                    val playerField2: SpaceField = getPlayerField(TEST_PLAYER_1)
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
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase(-2)

                    //move action
                    val target1: SpaceField = leftTopField
                    val target2: SpaceField = centerTopField
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
        SpaceEnvironment()
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
        SpaceEnvironment()
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
        SpaceEnvironment()
                .apply {
                    initGame(TEST_PLAYER_1, TEST_PLAYER_2, TEST_PLAYER_3)

                    assertCurrentPlayer(TEST_PLAYER_1)
                    assertCurrentPhase(Phase.MAIN1)

                    changePlayerTo(TEST_PLAYER_3)
                    assertCurrentPlayer(TEST_PLAYER_3)
                    assertCurrentPhase(Phase.MAIN1)
                }
    }

}