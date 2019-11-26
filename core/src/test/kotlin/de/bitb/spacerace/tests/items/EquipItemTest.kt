package de.bitb.spacerace.tests.items

import de.bitb.spacerace.core.assertDBPlayer
import de.bitb.spacerace.core.exceptions.ItemNotFoundException
import de.bitb.spacerace.env.TestEnvironment
import de.bitb.spacerace.env.equipItem
import de.bitb.spacerace.env.initGame
import de.bitb.spacerace.env.setPlayerItems
import de.bitb.spacerace.grafik.model.items.ItemInfo
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

    @Test
    fun noItemEquipped_removeIt_exception() {
        val item = ItemInfo.EngineIonInfo()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply {
                    assertDBPlayer(currentPlayerColor) { player ->
                        player.equippedItems.isEmpty() &&
                                player.storageItems.any { it.itemInfo.type == item.type }
                    }
                }
                .equipItem(
                        item,
                        equip = false,
                        error = ItemNotFoundException(item.type))
                .apply {
                    assertDBPlayer(currentPlayerColor) { player ->
                        player.equippedItems.isEmpty() &&
                                player.storageItems.any { it.itemInfo.type == item.type }
                    }
                }

    }
}