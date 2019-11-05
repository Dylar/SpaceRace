package de.bitb.spacerace.tests.items

import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.core.assertDBPlayer
import de.bitb.spacerace.env.*
import de.bitb.spacerace.grafik.model.items.ItemInfo
import org.junit.Test

class BuyItemTest : ItemsTest() {

    @Test
    fun activateItem_storageEmpty_itemEquipped() {
        val item = ItemInfo.FuelExtraInfo()
        TestEnvironment()
                .initGame()
                .apply { setToMain2Phase(shopField) }
                .assertDBPlayer(TEST_PLAYER_1) { it.storageItems.isEmpty() }
                .buyItem(item) { buyItemResult ->
                    val player = buyItemResult.playerData
                    START_CREDITS == player.credits + buyItemResult.itemData.itemInfo.price
                }
    }
}