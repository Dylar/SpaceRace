package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.core.GameTest
import de.bitb.spacerace.core.assertCurrentPhase
import de.bitb.spacerace.core.assertCurrentPlayer
import de.bitb.spacerace.core.assertNotCurrentPlayer
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_1
import de.bitb.spacerace.env.TEST_PLAYER_2
import de.bitb.spacerace.game.TestActions.Action.*
import de.bitb.spacerace.model.enums.Phase
import org.junit.Before
import org.junit.Test

class DiceUsecaseTest : GameTest() {

    @Before
    override fun setup() {
        super.setup()
    }

    @Test
    fun onlyCurrentPlayerCanDice() {
        SpaceEnvironment()
                .apply { initGame() }
                .also { env ->
                    //assert start
                    assertNotCurrentPlayer(env, TEST_PLAYER_2)
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN1)

                    DICE(TEST_PLAYER_1).doAction(env)
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

}