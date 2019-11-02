package de.bitb.spacerace.tests.fields

import de.bitb.spacerace.config.BITRISE_BORG
import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.core.assertCredits
import de.bitb.spacerace.core.assertCreditsNot
import de.bitb.spacerace.core.assertRoundCount
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.env.*
import de.bitb.spacerace.grafik.model.enums.FieldType
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class ObtainMineTestTest : ObtainFieldTest() {

    private fun dotest() {
//        assertTrue(true) //TODO bitrise bug...
        if (BITRISE_BORG) {
            var credits1: Int = START_CREDITS
            var credits2: Int = START_CREDITS
            var mine: FieldData? = null
            TestEnvironment()
                    .obtainField(FieldType.MINE)
                    .also {
                        mine = it.currentPlayer.mines.firstOrNull()
                        assertTrue(mine != null)
                        assertTrue(mine?.owner?.target?.playerColor == TEST_PLAYER_1)
                    }
                    .assertCredits(TEST_PLAYER_1, credits = credits1)
                    .assertRoundCount(1)
                    .assertCredits(TEST_PLAYER_1, credits1)
                    .assertCredits(TEST_PLAYER_2, credits2)
                    .endRound()
                    .assertCreditsNot(TEST_PLAYER_1, credits = credits1)
                    .assertRoundCount(2)
                    //check credits -> player1 got credits
                    .assertCreditsNot(TEST_PLAYER_1, credits = credits1)
                    .also { credits1 = it.getDBPlayer(TEST_PLAYER_1).credits }
                    .assertCredits(TEST_PLAYER_1, credits = credits1)
                    .assertCredits(TEST_PLAYER_2, credits = credits2)

                    //step between
                    .apply { endTurn(moveTo = leftBottomField) }
                    .apply { endTurn(moveTo = leftBottomField) }
                    .endRound()
                    .assertRoundCount(3)

                    //check credits -> player1 got more credits
                    .assertCreditsNot(TEST_PLAYER_1, credits = credits1)
                    .also { credits1 = it.getDBPlayer(TEST_PLAYER_1).credits }
                    .assertCredits(TEST_PLAYER_1, credits = credits1)
                    .assertCredits(TEST_PLAYER_2, credits = credits2)

                    //steal mine
                    .apply { endTurn(moveTo = leftSideField) }
                    .apply { endTurn(moveTo = leftTopField) }
                    .endRound()
                    .assertRoundCount(4)

                    //check credits -> player2 got credits + player1 got none
                    .assertCredits(TEST_PLAYER_1, credits = credits1)
                    .assertCreditsNot(TEST_PLAYER_2, credits = credits2)
                    .also { credits2 = it.getDBPlayer(TEST_PLAYER_2).credits }
                    .assertCredits(TEST_PLAYER_2, credits = credits2)

                    .also { env ->
                        mine?.also { field ->
                            mine = env.getDBField(field.uuid)
                            assertTrue(mine != null)
                            assertTrue(mine?.owner?.target?.playerColor == TEST_PLAYER_2)
                        } ?: fail()
                    }
        }
    }

    @Test //TODO FLAKY :( ?
    fun notCredits1() {
        dotest()
    }

    @Test //TODO FLAKY :( ?
    fun notCredits2() {
        dotest()
    }

    @Test //TODO FLAKY :( ?
    fun notCredi3ts() {
        dotest()
    }

    @Test //TODO FLAKY :( ?
    fun notCredit4s() {
        dotest()
    }


    @Test //TODO FLAKY :( ?
    fun notCred5its() {
        dotest()
    }

    @Test //TODO FLAKY :( ?
    fun notCredi6ts() {
        dotest()
    }

    @Test //TODO FLAKY :( ?
    fun notCre7dits() {
        dotest()
    }

    @Test //TODO FLAKY :( ?
    fun notCredit8s() {
        dotest()
    }


    @Test //TODO FLAKY :( ?
    fun notCredit9s() {
        dotest()
    }

    @Test //TODO FLAKY :( ?
    fun notCredits0() {
        dotest()
    }

    @Test //TODO FLAKY :( ?
    fun notCredits11() {
        dotest()
    }

    @Test //TODO FLAKY :( ?
    fun notCredits12() {
        dotest()
    }

    @Test //TODO FLAKY :( ?
    fun notCredits13() {
        dotest()
    }

    @Test //TODO FLAKY :( ?
    fun credits() {
//        assertTrue(true) //TODO bitrise bug...
        if (BITRISE_BORG) {
            var credits1: Int = START_CREDITS
            var credits2: Int = START_CREDITS
            var mine: FieldData? = null
            TestEnvironment()
                    .obtainField(FieldType.MINE)
                    .also {
                        mine = it.currentPlayer.mines.firstOrNull()
                        assertTrue(mine != null)
                        assertTrue(mine?.owner?.target?.playerColor == TEST_PLAYER_1)
                    }
                    .assertRoundCount(1)
                    .assertCredits(TEST_PLAYER_1, credits1)
                    .assertCredits(TEST_PLAYER_2, credits2)
                    .endRound()
                    .assertRoundCount(2)
                    //check credits -> player1 got credits
                    .assertCredits(TEST_PLAYER_1, credits = credits1)
                    .also { credits1 = it.getDBPlayer(TEST_PLAYER_1).credits }
                    .assertCredits(TEST_PLAYER_1, credits = credits1)
                    .assertCredits(TEST_PLAYER_2, credits = credits2)

                    //step between
                    .apply { endTurn(moveTo = leftBottomField) }
                    .apply { endTurn(moveTo = leftBottomField) }
                    .endRound()
                    .assertRoundCount(3)

                    //check credits -> player1 got more credits
                    .assertCreditsNot(TEST_PLAYER_1, credits = credits1)
                    .also { credits1 = it.getDBPlayer(TEST_PLAYER_1).credits }
                    .assertCredits(TEST_PLAYER_1, credits = credits1)
                    .assertCredits(TEST_PLAYER_2, credits = credits2)

                    //steal mine
                    .apply { endTurn(moveTo = leftSideField) }
                    .apply { endTurn(moveTo = leftTopField) }
                    .endRound()
                    .assertRoundCount(4)

                    //check credits -> player2 got credits + player1 got none
                    .assertCredits(TEST_PLAYER_1, credits = credits1)
                    .assertCreditsNot(TEST_PLAYER_2, credits = credits2)
                    .also { credits2 = it.getDBPlayer(TEST_PLAYER_2).credits }
                    .assertCredits(TEST_PLAYER_2, credits = credits2)

                    .also { env ->
                        mine?.also { field ->
                            mine = env.getDBField(field.uuid)
                            assertTrue(mine != null)
                            assertTrue(mine?.owner?.target?.playerColor == TEST_PLAYER_2)
                        } ?: fail()
                    }
        }
    }

}