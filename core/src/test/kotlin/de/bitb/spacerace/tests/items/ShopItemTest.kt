package de.bitb.spacerace.tests.items

import de.bitb.spacerace.config.ITEM_SELL_MOD
import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.core.assertDBPlayer
import de.bitb.spacerace.env.*
import de.bitb.spacerace.grafik.model.items.ItemInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class ShopItemTest : ItemsTest() {

    @Test
    fun storageEmpty_buyItem_itemInStorage_creditsReduced_itemInDB() {
        val item = ItemInfo.FuelExtraInfo()
        TestEnvironment()
                .initGame()
                .also { inject() }
                .apply { setToMain2Phase(shopField) }
                .assertDBPlayer(TEST_PLAYER_1) { it.storageItems.isEmpty() }
                .apply { assertEquals(0, itemDataSource.getAllDBItems().size) }
                .buyItem(item) { (player, itemData) ->
                    val itemInfo = itemData.itemInfo
                    val creditsReduced = START_CREDITS == player.credits + itemInfo.price
                    creditsReduced && player.storageItems.size == 1
                }
                .apply { assertEquals(1, itemDataSource.getAllDBItems().size) }
    }

    @Test
    fun itemInStorage_sellItem_storageEmpty_creditsIncreased_notFullPrice_itemNotInDB() {
        val item = ItemInfo.FuelExtraInfo()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .also { inject() }
                .apply { setToMain2Phase(shopField) }
                .assertDBPlayer(TEST_PLAYER_1) { it.storageItems.isNotEmpty() }
                .apply { assertEquals(2, itemDataSource.getAllDBItems().size) }
                .sellItem(item) { (player, itemData) ->
                    val itemInfo = itemData.itemInfo
                    val creditsNotFullPrice = player.credits != START_CREDITS + itemInfo.price
                    val creditsReducedPrice = player.credits == (START_CREDITS + itemInfo.price * ITEM_SELL_MOD).toInt()
                    val dbItems = itemDataSource.getDBItems(itemData.id)
                    assertEquals(1, itemDataSource.getAllDBItems().size)
                    creditsNotFullPrice && creditsReducedPrice && player.storageItems.isEmpty() && dbItems.isEmpty()
                }
    }
}