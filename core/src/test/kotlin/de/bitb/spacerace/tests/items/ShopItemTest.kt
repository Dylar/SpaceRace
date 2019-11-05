package de.bitb.spacerace.tests.items

import de.bitb.spacerace.config.ITEM_SELL_MOD
import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.core.assertDBPlayer
import de.bitb.spacerace.env.*
import de.bitb.spacerace.grafik.model.items.ItemInfo
import org.junit.Test

class ShopItemTest : ItemsTest() {

    @Test
    fun storageEmpty_buyItem_itemInStorage_creditsReduced() {
        val item = ItemInfo.FuelExtraInfo()
        TestEnvironment()
                .initGame()
                .apply { setToMain2Phase(shopField) }
                .assertDBPlayer(TEST_PLAYER_1) { it.storageItems.isEmpty() }
                .buyItem(item) { (player, itemData) ->
                    val itemInfo = itemData.itemInfo
                    val creditsReduced = START_CREDITS == player.credits + itemInfo.price
                    creditsReduced && player.storageItems.isNotEmpty()
                }
    }

    @Test
    fun itemInStorage_sellItem_storageEmpty_creditsIncreased_notFullPrice() {
        val item = ItemInfo.FuelExtraInfo()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply { setToMain2Phase(shopField) }
                .assertDBPlayer(TEST_PLAYER_1) { it.storageItems.isNotEmpty() }
                .sellItem(item) { (player, itemData) ->
                    val itemInfo = itemData.itemInfo
                    val creditsNotFullPrice = player.credits != START_CREDITS + itemInfo.price
                    val creditsReducedPrice = player.credits == (START_CREDITS + itemInfo.price * ITEM_SELL_MOD).toInt()
                    creditsNotFullPrice && creditsReducedPrice && player.storageItems.isEmpty()
                }
    }
}