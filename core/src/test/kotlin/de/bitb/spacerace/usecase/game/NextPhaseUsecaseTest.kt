package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.GameTest
import de.bitb.spacerace.TestGame
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.enums.Phase
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class NextPhaseUsecaseTest : GameTest() {

    @Inject
    lateinit var nextPhaseUseCase: NextPhaseUsecase
    @Inject
    lateinit var playerController: PlayerController

    @Before
    override fun setup() {
        super.setup()
        TestGame.testComponent.inject(this)
    }

    private fun callNextPhase(currentPlayer: PlayerData) {
        nextPhaseUseCase
                .buildUseCaseCompletable(currentPlayer)
                .test()
                .await()
                .assertComplete()
    }

    @Test
    fun simpleAssert() {
        val a = 1
        val b = 1
        Thread.sleep(3000)
        assertTrue(a + b == 2)
    }

    @Test
    fun bla() {
        playerController.currentPlayerData.also {
            val prePhase = it.phase
            callNextPhase(it)
            val postPhase = playerController.currentPlayerData.phase
            assertThat(prePhase, not(`is`(postPhase)))
            assertThat(prePhase, `is`(Phase.MAIN1))
            assertThat(postPhase, `is`(Phase.MAIN1))
        }


        // when
//                    test.assertComplete()
//                .assertValue { it }
    }

}