package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.GameTest
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NextPhaseUsecaseTest : GameTest() {

    @Before
    override fun setup() {
        super.setup()
//        TestGame.testComponent.inject(this)
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
        testGame
                ?.let {
                    val playerController = it.playerController
                    val nextPhaseUseCase = NextPhaseUsecase(playerController, it.fieldController, it.playerDataSource, it.playerColorDispender)
                    nextPhaseUseCase
                            .buildUseCaseCompletable(playerController.currentPlayerData)
                            .test()
                }
                ?.apply {
                    await()
                    assertComplete()
                    // when
//                    test.assertComplete()
//                .assertValue { it }
                }

    }
}