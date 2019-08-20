package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.*
import de.bitb.spacerace.TestActions.Action.DICE
import de.bitb.spacerace.TestActions.Action.NEXT_PHASE
import de.bitb.spacerace.model.enums.Phase
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
    fun noDice_nextPhaseClicked_failure_stillMainPhase() {
        SpaceEnvironment()
                .apply { initGame() }
                .also { env ->
                    //assert start
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN1)

                    //do next phase action
                    val action = NEXT_PHASE(env.currentPlayer.playerColor)
                    TestActions.doAction(env, action)

                    //assert still same phase
                    assertCurrentPhase(env, Phase.MAIN1)
                }
        // when
//                    test.assertComplete()
//                .assertValue { it }
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
                    var action : TestActions.Action = DICE(env.currentPlayerColor)
                    TestActions.doAction(env, action)

                    //do next phase action
                    action = NEXT_PHASE(env.currentPlayer.playerColor)
                    TestActions.doAction(env, action)

                    //assert move phase
                    assertCurrentPhase(env, Phase.MOVE)
                }
        // when
//                    test.assertComplete()
//                .assertValue { it }
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
                    var action : TestActions.Action = DICE(env.currentPlayerColor)
                    TestActions.doAction(env, action)

                    //do next phase action
                    action = NEXT_PHASE(env.currentPlayer.playerColor)
                    TestActions.doAction(env, action)

                    //assert move phase
                    assertCurrentPhase(env, Phase.MOVE)
                }
        // when
//                    test.assertComplete()
//                .assertValue { it }
    }

}