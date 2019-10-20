package de.bitb.spacerace.tests.items

import de.bitb.spacerace.env.TestEnvironment
import de.bitb.spacerace.env.activateItem
import de.bitb.spacerace.env.initGame
import de.bitb.spacerace.env.setPlayerItems
import de.bitb.spacerace.exceptions.ItemNotFoundException
import de.bitb.spacerace.model.items.ItemInfo
import org.junit.Assert.assertTrue
import org.junit.Test

class ActivateItemTest : ItemsTest() {

    @Test
    fun equipItem_storageEmpty_itemEquipped() {
        val item = ItemInfo.EXTRA_FUEL()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply { assertTrue(getDBPlayer(currentPlayerColor).storageItems.isNotEmpty()) }
                .activateItem(item) { equipResult ->
                    equipResult.playerData.storageItems.isEmpty() &&
                            equipResult.playerData.activeItems.any { it.itemInfo.name == item.name }
                }
    }

    @Test
    fun equipItem_removeIt_itemInStorage_onlyEquipOnce() {
        val item = ItemInfo.EXTRA_FUEL()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply { assertTrue(getDBPlayer(currentPlayerColor).storageItems.isNotEmpty()) }
                .activateItem(item) { equipResult ->
                    equipResult.playerData.storageItems.isEmpty() &&
                            equipResult.playerData.activeItems.any { it.itemInfo.name == item.name }
                }.activateItem(item, error = ItemNotFoundException(item))
    }
}