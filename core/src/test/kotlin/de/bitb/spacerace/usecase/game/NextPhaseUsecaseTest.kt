package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_1
import de.bitb.spacerace.env.TEST_PLAYER_2
import de.bitb.spacerace.env.TEST_PLAYER_3
import de.bitb.spacerace.game.TestActions.Action.*
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.space.fields.SpaceField
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NextPhaseUsecaseTest : GameTest() {

    @Before
    override fun setup() {
        super.setup()
    }

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
                    DICE(currentPlayerColor).doAction(this)
                }
                .also { env ->
                    //assert start
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN1)

                    //do next phase action with not current player
                    NEXT_PHASE(TEST_PLAYER_2).doAction(env)

                    //assert still same phase
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN1)

                    NEXT_PHASE(TEST_PLAYER_1).doAction(env)
                    assertCurrentPhase(env, Phase.MOVE)
                }
    }

    @Test
    fun onlyCurrentPlayerCanChangePhase_MoveToMain2() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()
                    MOVE(currentPlayerColor, defaultField1).doAction(this)
                }
                .also { env ->
                    //assert start
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MOVE)

                    //do next phase action with not current player
                    NEXT_PHASE(TEST_PLAYER_2).doAction(env)

                    //assert still same phase
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MOVE)

                    NEXT_PHASE(TEST_PLAYER_1).doAction(env)
                    assertCurrentPhase(env, Phase.MAIN2)
                }
    }

    @Test
    fun onlyCurrentPlayerCanChangePhase_Main2ToMain1_playerChanged() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMain2Phase()
                }
                .also { env ->
                    //assert start
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN2)

                    //do next phase action with not current player
                    NEXT_PHASE(TEST_PLAYER_2).doAction(env)

                    //assert still same phase
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN2)

                    NEXT_PHASE(TEST_PLAYER_1).doAction(env)

                    //assert player changed
                    assertNotCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPhase(env, Phase.MAIN1)
                }
    }

    @Test
    fun noDice_nextPhaseClicked_failure_stillMainPhase() {
        SpaceEnvironment()
                .apply { initGame() }
                .also { env ->
                    //assert start
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN1)

                    //do next phase action
                    NEXT_PHASE(env.currentPlayerColor).doAction(env)

                    //assert still same phase
                    assertCurrentPhase(env, Phase.MAIN1)
                }
    }

    @Test
    fun dice_nextPhaseClicked_success_movePhase() {
        SpaceEnvironment()
                .apply { initGame() }
                .also { env ->
                    //assert start
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN1)

                    //do dice action
                    DICE(env.currentPlayerColor).doAction(env)

                    //do next phase action
                    NEXT_PHASE(env.currentPlayer.playerColor).doAction(env)

                    //assert move phase
                    assertCurrentPhase(env, Phase.MOVE)
                }
    }


    @Test
    fun movePhase_nextPhaseClicked_failure_movePhase_stepsLeft() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()
                }
                .also { env ->
                    NEXT_PHASE(env.currentPlayerColor).doAction(env)

                    //assert move phase
                    assertCurrentPhase(env, Phase.MOVE)
                }
    }

    @Test
    fun movePhase_move_nextPhaseClicked_success_main2Phase() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()
                }
                .also { env ->
                    //get move target
                    val target: SpaceField = env.getRandomConnectedField()

                    //move action
                    MOVE(env.currentPlayerColor, target).doAction(env)
                    NEXT_PHASE(env.currentPlayerColor).doAction(env)

                    assertCurrentPhase(env, Phase.MAIN2)
                }
    }

    @Test
    fun movePhase_move1_nextPhaseClicked_failure_movePhase_stepsLeft_move1_nextPhaseSuccess() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase(-2)
                }
                .also { env ->
                    val target1: SpaceField = env.defaultField1
                    val target2: SpaceField = env.defaultField2
                    assertTargetNotSame(target1, target2)
                    val playerField1: SpaceField = env.getPlayerField(TEST_PLAYER_1)

                    //move1
                    MOVE(env.currentPlayerColor, target1).doAction(env)
                    val playerField2: SpaceField = env.getPlayerField(TEST_PLAYER_1)
                    assertTargetNotSame(playerField1, playerField2)

                    //next phase failed
                    NEXT_PHASE(env.currentPlayerColor).doAction(env)
                    assertCurrentPhase(env, Phase.MOVE)

                    //move2
                    MOVE(env.currentPlayerColor, target2).doAction(env)

                    //next phase success
                    NEXT_PHASE(env.currentPlayerColor).doAction(env)

                    assertCurrentPhase(env, Phase.MAIN2)
                }
    }

    @Test
    fun movePhase_move2_nextPhaseClicked_success_main2Phase() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase(-2)
                }
                .also { env ->
                    //move action
                    val target1: SpaceField = env.defaultField1
                    val target2: SpaceField = env.defaultField2
                    assertTargetNotSame(target1, target2)

                    MOVE(env.currentPlayerColor, target1).doAction(env)
                    assertPlayerOnField(env, TEST_PLAYER_1, target1)
                    MOVE(env.currentPlayerColor, target2).doAction(env)

                    NEXT_PHASE(env.currentPlayerColor).doAction(env)

                    //assert main2 phase
                    assertCurrentPhase(env, Phase.MAIN2)
                }
    }

    @Test
    fun main2Phase_nextPhaseClicked_success_currentPlayerChanged() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMain2Phase()
                }
                .also { env ->
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    NEXT_PHASE(env.currentPlayerColor).doAction(env)

                    //assert main1 phase
                    assertCurrentPhase(env, Phase.MAIN1)
                    assertCurrentPlayer(env, TEST_PLAYER_2)
                }
    }

    @Test
    fun changePlayer1_toPlayer2() {
        SpaceEnvironment()
                .apply {
                    initGame()
                }
                .also { env ->
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN1)

                    env.changePlayerTo(TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPhase(env, Phase.MAIN1)
                }
    }

    @Test
    fun changePlayer1_skipPlayer2_toPlayer3() {
        SpaceEnvironment()
                .apply {
                    initGame(TEST_PLAYER_1, TEST_PLAYER_2, TEST_PLAYER_3)
                }
                .also { env ->
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN1)

                    env.changePlayerTo(TEST_PLAYER_3)
                    assertCurrentPlayer(env, TEST_PLAYER_3)
                    assertCurrentPhase(env, Phase.MAIN1)
                }
    }

}