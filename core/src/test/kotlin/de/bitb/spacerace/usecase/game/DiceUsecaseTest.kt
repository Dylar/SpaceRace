package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.core.GameTest
import de.bitb.spacerace.core.assertCurrentPhase
import de.bitb.spacerace.core.assertCurrentPlayer
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_1
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
    fun noDice_nextPhaseClicked_failure_stillMainPhase() {
        SpaceEnvironment()
                .apply { initGame() }
                .also { env ->
                    //assert start
                    assertCurrentPlayer(env, TEST_PLAYER_1)
                    assertCurrentPhase(env, Phase.MAIN1)

                    //do next phase action
                    NEXT_PHASE(env.currentPlayer.playerColor).doAction(env)

                    //assert still same phase
                    assertCurrentPhase(env, Phase.MAIN1)
                }
    }

}