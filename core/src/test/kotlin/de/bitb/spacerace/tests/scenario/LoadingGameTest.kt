package de.bitb.spacerace.tests.scenario

import de.bitb.spacerace.core.GameTest
import de.bitb.spacerace.env.SpaceEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_1
import de.bitb.spacerace.exceptions.SelectMorePlayerException
import org.junit.Test

class LoadingGameTest : GameTest() {

    @Test
    fun initGame_failure_selectMorePlayerException() {
        SpaceEnvironment()
                .apply {
                    initGame(TEST_PLAYER_1,
                            error = SelectMorePlayerException(),
                            assertError = { true })
                }
    }
}