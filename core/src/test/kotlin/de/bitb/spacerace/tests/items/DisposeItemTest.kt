package de.bitb.spacerace.tests.items

import de.bitb.spacerace.env.*
import de.bitb.spacerace.grafik.model.items.ItemInfo
import org.junit.Assert.assertTrue
import org.junit.Test

class DisposeItemTest : ItemsTest() {

    @Test
    fun disposeItem_storageEmpty_itemOnField() {
        val item = ItemInfo.MineSlowInfo()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply { assertTrue(getDBPlayer(currentPlayerColor).storageItems.isNotEmpty()) }
                .disposeItem(item) { disposeResult ->
                    disposeResult.playerData.storageItems.isEmpty() &&
                            disposeResult.playerData.positionField.target.disposedItems.any { it.itemInfo.type == item.type }
                }
    }

    @Test
    fun disposeItem_collectItem_itemNotOnField() {
        val item = ItemInfo.MineSlowInfo()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .setGiftFieldItems { listOf(item) }
                .initGame()
                .setToMain2Phase()
                .apply {
                    assertTrue(currentPlayer.positionField.target.disposedItems.isEmpty())
                    assertTrue(currentPlayer.storageItems.filter { it.itemInfo.type == item.type }.size == 2)
                }
                .disposeItem(item) { result ->
                    result.playerData.positionField.target.disposedItems.any { it.itemInfo.type == item.type } &&
                            result.playerData.storageItems.filter { it.itemInfo.type == item.type }.size == 1
                }
                .endTurn()
                .apply { assertTrue(currentPlayer.attachedItems.isEmpty()) }
                .setToMain2Phase()
                .apply {
                    assertTrue(currentPlayer.attachedItems.any { it.itemInfo.type == item.type })

                    val dbField = getDBField(currentPlayer.positionField.target.uuid)
                    assertTrue(dbField.disposedItems.isEmpty())
                    val playerField = currentPlayer.positionField.target
                    assertTrue(playerField.disposedItems.isEmpty())
                }
    }
}