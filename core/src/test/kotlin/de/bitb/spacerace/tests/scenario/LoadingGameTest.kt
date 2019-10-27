package de.bitb.spacerace.tests.scenario

import de.bitb.spacerace.core.GameTest
import de.bitb.spacerace.env.TestEnvironment
import de.bitb.spacerace.env.TEST_PLAYER_1
import de.bitb.spacerace.env.initGame
import de.bitb.spacerace.core.exceptions.SelectMorePlayerException
import org.junit.Test

class LoadingGameTest : GameTest() {

    @Test
    fun initGame_failure_selectMorePlayerException() {
        TestEnvironment()
                .apply {
                    initGame(TEST_PLAYER_1,
                            error = SelectMorePlayerException(),
                            assertError = { true })
                }
    }

    @Test
    fun loadGame_success() {
        TestEnvironment()
                .apply {
//                    initGame(TEST_PLAYER_1,
//                            error = SelectMorePlayerException(),
//                            assertError = { true })
                }
    }
}