package de.bitb.spacerace.tests.items

import de.bitb.spacerace.core.GameTest
import de.bitb.spacerace.env.TestEnvironment
import de.bitb.spacerace.env.equipItem
import de.bitb.spacerace.env.initGame
import de.bitb.spacerace.env.setPlayerItems
import de.bitb.spacerace.model.items.ItemInfo
import org.junit.Assert.assertTrue
import org.junit.Test

class EquipItemTest : GameTest() {

    @Test
    fun equipItem_receiveSetItemIntoStorage() {
        val item = ItemInfo.ION_ENGINE()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply { assertTrue(getDBPlayer(currentPlayerColor).storageItems.isNotEmpty()) }
                .equipItem(item) { equipResult ->
                    equipResult.player.storageItems.isEmpty() &&
                            equipResult.player.equippedItems.any { it.itemInfo.name == item.name }
                }
    }
}