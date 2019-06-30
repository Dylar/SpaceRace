package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.GameTest
import de.bitb.spacerace.TestGame
import de.bitb.spacerace.database.player.PlayerDataSource
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class NextPhaseUsecaseTest : GameTest() {

    private val box: PlayerDataSource = mockk()

    @Inject
    lateinit var nextPhaseUseCase: NextPhaseUsecase

    @Before
    override fun setup() {
        super.setup()
        TestGame.testComponent.inject(this)
    }

    @Test
    fun bla() {

//        Setup.trampilineSchedulers()

        val test = nextPhaseUseCase
                .buildUseCaseObservable(mockk())
                .test()

        test.await()

        // when
        test.assertValueCount(1)
                .assertValue {
                    // then
                    it
                }
    }
}