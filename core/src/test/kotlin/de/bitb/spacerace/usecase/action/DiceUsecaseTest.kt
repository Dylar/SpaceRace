package de.bitb.spacerace.usecase.action

import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_1
import de.bitb.spacerace.env.TEST_PLAYER_2
import de.bitb.spacerace.model.enums.Phase
import org.junit.Test

class DiceUsecaseTest : GameTest() {

    @Test
    fun onlyCurrentPlayerCanDice_inMain1Phase() {
        SpaceEnvironment()
                .apply { initGame() }
                .also { env ->
                    //assert start
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN1)

                    assertDiceResult(env, 0, TEST_PLAYER_1)
                    assertDiceResult(env, 0, TEST_PLAYER_2)

                    env.dice(TEST_PLAYER_2)

                    assertDiceResult(env, 0, TEST_PLAYER_1)
                    assertDiceResult(env, 0, TEST_PLAYER_2)

                    env.dice(TEST_PLAYER_1)

                    assertDiceResult(env, 1, TEST_PLAYER_1)
                    assertDiceResult(env, 0, TEST_PLAYER_2)
                }
    }

    @Test
    fun nobodyCanDice_inMovePhase() {
        SpaceEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()
                }
                .also { env ->
                    //assert start
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MOVE)

                    assertDiceResult(env, 1, TEST_PLAYER_1)
                    assertDiceResult(env, 0, TEST_PLAYER_2)

                    env.dice(TEST_PLAYER_2)

                    assertDiceResult(env, 1, TEST_PLAYER_1)
                    assertDiceResult(env, 0, TEST_PLAYER_2)

                    env.dice(TEST_PLAYER_1)

                    assertDiceResult(env, 1, TEST_PLAYER_1)
                    assertDiceResult(env, 0, TEST_PLAYER_2)
                }
    }

    @Test
    fun nobodyCanDice_InMain2Phase() {
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

                    assertDiceResult(env, 0, TEST_PLAYER_1)
                    assertDiceResult(env, 0, TEST_PLAYER_2)

                    env.dice(TEST_PLAYER_2)

                    assertDiceResult(env, 0, TEST_PLAYER_1)
                    assertDiceResult(env, 0, TEST_PLAYER_2)

                    env.dice(TEST_PLAYER_1)

                    assertDiceResult(env, 0, TEST_PLAYER_1)
                    assertDiceResult(env, 0, TEST_PLAYER_2)
                }
    }

}