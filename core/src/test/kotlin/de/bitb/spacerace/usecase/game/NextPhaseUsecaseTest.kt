package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.GameTest
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
//        var nextPhaseUseCase = NextPhaseUsecase(testGame.)
//        Setup.trampilineSchedulers()
//
//        val test = nextPhaseUseCase
//                .buildUseCaseFlowable(mockk())
//                .test()
//
//        test.await()
//
//        // when
//        test.assertValueCount(1)
//                .assertValue {
//                    // then
//                    it
//                }
    }
}