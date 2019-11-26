package de.bitb.spacerace.tests.items.effect

import de.bitb.spacerace.core.assertDBPlayer
import de.bitb.spacerace.env.*
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.tests.items.ItemsTest
import org.junit.Test

class MovableItemTest : ItemsTest() {

//    @Test
//    fun disposeItem_storageEmpty_itemOnField() {
//        val item = ItemInfo.MineSlowInfo()
//        TestEnvironment()
//                .setPlayerItems { listOf(item) }
//                .initGame()
//                .apply { assertTrue(getDBPlayer(currentPlayerColor).storageItems.isNotEmpty()) }
//                .disposeItem(item) { disposeResult ->
//                    disposeResult.playerData.storageItems.isEmpty() &&
//                            disposeResult.playerData.positionField.target.disposedItems.any { it.itemInfo.type == item.type }
//                }
//    }

    @Test
    fun disposeItem_collectItem_itemNotOnField() {
        val item = ItemInfo.MineMovingInfo()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .setGiftFieldItems { listOf(item) }
                .initGame()
                .setToMain2Phase()
                .disposeItem(item)
                .assertDBPlayer(TEST_PLAYER_1) { player ->
                    val field = player.positionField.target
                    val itemOnField = field.disposedItems.isNotEmpty()
                    val itemNotOnConnection = field.connections.all { it.disposedItems.isEmpty() }
                    itemOnField && itemNotOnConnection
                }
                .endRound()
                .assertDBPlayer(TEST_PLAYER_1) { player ->
                    val field = player.positionField.target
                    val itemNotOnField = field.disposedItems.isEmpty()
                    val itemOnConnection = field.connections.any { it.disposedItems.isNotEmpty() }
                    itemNotOnField && itemOnConnection
                }
    }
}