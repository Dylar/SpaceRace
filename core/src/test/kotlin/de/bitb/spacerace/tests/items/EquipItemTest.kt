package de.bitb.spacerace.tests.items

import de.bitb.spacerace.env.TestEnvironment
import de.bitb.spacerace.env.equipItem
import de.bitb.spacerace.env.initGame
import de.bitb.spacerace.env.setPlayerItems
import de.bitb.spacerace.exceptions.ItemNotFoundException
import de.bitb.spacerace.model.items.ItemInfo
import org.junit.Assert.assertTrue
import org.junit.Test

class EquipItemTest : ItemsTest() {

    @Test
    fun equipItem_storageEmpty_itemEquipped_canOnlyEquipOnce() {
        val item = ItemInfo.EngineIonInfo()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply { assertTrue(getDBPlayer(currentPlayerColor).storageItems.isNotEmpty()) }
                .equipItem(item) { equipResult ->
                    equipResult.playerData.storageItems.isEmpty() &&
                            equipResult.playerData.equippedItems.any { it.itemInfo.type == item.type }
                }.equipItem(item, error = ItemNotFoundException(item.type))
    }

    @Test
    fun equipItem_removeIt_itemInStorage() {
        val item = ItemInfo.EngineIonInfo()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply { assertTrue(getDBPlayer(currentPlayerColor).storageItems.isNotEmpty()) }
                .equipItem(item) { equipResult ->
                    equipResult.playerData.storageItems.isEmpty() &&
                            equipResult.playerData.equippedItems.any { it.itemInfo.type == item.type }
                }.equipItem(item, equip = false) { equipResult ->
                    equipResult.playerData.equippedItems.isEmpty() &&
                            equipResult.playerData.storageItems.any { it.itemInfo.type == item.type }
                }
    }
}