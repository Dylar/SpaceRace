package de.bitb.spacerace.tests.usecase.action

import de.bitb.spacerace.core.*
import de.bitb.spacerace.env.*
import de.bitb.spacerace.exceptions.NotCurrentPlayerException
import de.bitb.spacerace.model.enums.Phase
import org.junit.Test

class DiceUsecaseTest : GameTest() {

    @Test
    fun onlyCurrentPlayerCanDice_inMain1Phase() {
        TestEnvironment()
                .initGame()
                .apply {
                    //assert start
                    assertNotCurrentPlayer( TEST_PLAYER_2)
                    assertCurrentPlayer( TEST_PLAYER_1)
                    assertCurrentPhase( Phase.MAIN1)

                    assertDiceResult(0, TEST_PLAYER_1)
                    assertDiceResult(0, TEST_PLAYER_2)

                    dice(
                            error = NotCurrentPlayerException(TEST_PLAYER_2),
                            player = TEST_PLAYER_2
                    )

                    assertDiceResult(0, TEST_PLAYER_1)
                    assertDiceResult(0, TEST_PLAYER_2)

                    dice(TEST_PLAYER_1)

                    assertDiceResult(1, TEST_PLAYER_1)
                    assertDiceResult(0, TEST_PLAYER_2)
                }
    }

    @Test
    fun nobodyCanDice_inMovePhase() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMovePhase()

                    //assert start
                    assertNotCurrentPlayer( TEST_PLAYER_2)
                    assertCurrentPlayer( TEST_PLAYER_1)
                    assertCurrentPhase( Phase.MOVE)

                    assertDiceResult(1, TEST_PLAYER_1)
                    assertDiceResult(0, TEST_PLAYER_2)

                    dice(
                            error = NotCurrentPlayerException(TEST_PLAYER_2),
                            player = TEST_PLAYER_2
                    )

                    assertDiceResult(1, TEST_PLAYER_1)
                    assertDiceResult(0, TEST_PLAYER_2)

                    dice(TEST_PLAYER_1)

                    assertDiceResult(1, TEST_PLAYER_1)
                    assertDiceResult(0, TEST_PLAYER_2)
                }
    }

    @Test
    fun nobodyCanDice_InMain2Phase() {
        TestEnvironment()
                .apply {
                    initGame()
                    setToMain2Phase()

                    //assert start
                    assertNotCurrentPlayer( TEST_PLAYER_2)
                    assertCurrentPlayer( TEST_PLAYER_1)
                    assertCurrentPhase( Phase.MAIN2)

                    assertDiceResult(0, TEST_PLAYER_1)
                    assertDiceResult(0, TEST_PLAYER_2)

                    dice(
                            error = NotCurrentPlayerException(TEST_PLAYER_2),
                            player = TEST_PLAYER_2
                    )

                    assertDiceResult(0, TEST_PLAYER_1)
                    assertDiceResult(0, TEST_PLAYER_2)

                    dice(TEST_PLAYER_1)

                    assertDiceResult(0, TEST_PLAYER_1)
                    assertDiceResult(0, TEST_PLAYER_2)
                }
    }

}