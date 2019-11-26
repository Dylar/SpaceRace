package de.bitb.spacerace.tests.items.effect

import de.bitb.spacerace.core.assertDBPlayer
import de.bitb.spacerace.core.exceptions.MoreDiceException
import de.bitb.spacerace.env.*
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.tests.items.ItemsTest
import org.junit.Test

class MultiDiceItemTest : ItemsTest() {

    @Test
    fun activateItem_diceOnce_cantContinue_diceAgain_canContinue_BOOST_SPEED() {
        val item = ItemInfo.BoostSpeedInfo()
        
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply {
                    assertDBPlayer(currentPlayerColor) { it.maxDice() == 1 && it.diceResults.size == 0 }
                    nextPhase(error = MoreDiceException(currentPlayerColor, 0, 1))
                    activateItem(item)
                    assertDBPlayer(currentPlayerColor) { it.maxDice() == 2 && it.diceResults.size == 0 }
                    nextPhase(error = MoreDiceException(currentPlayerColor, 0, 2))
                    dice()
                    assertDBPlayer(currentPlayerColor) { it.maxDice() == 2 && it.diceResults.size == 1 }
                    nextPhase(error = MoreDiceException(currentPlayerColor, 1, 2))
                    dice()
                    assertDBPlayer(currentPlayerColor) { it.maxDice() == it.diceResults.size }
                    nextPhase { it.player.phase.isMoving() }
                }
    }

}