package de.bitb.spacerace.tests.fields

import de.bitb.spacerace.config.BITRISE_BORG
import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.core.assertCredits
import de.bitb.spacerace.core.assertCreditsNot
import de.bitb.spacerace.core.assertRoundCount
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.env.*
import de.bitb.spacerace.model.enums.FieldType
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class ObtainMineTest : ObtainFieldTest() {

    @Test
    fun obtainMine_playerGotMine() {
        TestEnvironment()
                .obtainField(FieldType.MINE) {
                    val player = it.player
                    val field = player.positionField.target
                    val mine = player.mines.first()

                    field.uuid == mine.uuid
                }
    }

    @Test
    fun obtainMine_endRound_GotMoney() {
//        assertTrue(true) //TODO bitrise bug...
        if (BITRISE_BORG) {
            TestEnvironment()
                    .obtainField(FieldType.MINE)
                    .assertCredits()
                    .endRound()
                    .assertCreditsNot(credits = START_CREDITS)
        }
    }

    @Test
    fun obtainMine_endRound_GotMoney_stealMine() {
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

}