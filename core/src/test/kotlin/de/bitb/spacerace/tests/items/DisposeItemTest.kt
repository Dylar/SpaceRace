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
                .initGame()
                .setToMain2Phase()
                .apply {
                    assertTrue(currentPlayer.positionField.target.disposedItems.isEmpty())
                    assertTrue(currentPlayer.storageItems.filter { it.itemInfo.type == item.type }.size == 2)
                }
                .disposeItem(item)
                .apply {
                    assertTrue(currentPlayer.positionField.target.disposedItems.any { it.itemInfo.type == item.type })
                    assertTrue(currentPlayer.storageItems.filter { it.itemInfo.type == item.type }.size == 1)
                }
                .endTurn()
                .apply { assertTrue(currentPlayer.attachedItems.isEmpty()) }
                .setToMain2Phase()
                .apply {
                    assertTrue(currentPlayer.attachedItems.any { it.itemInfo.type == item.type })

                    val dbField = getDBField(currentPlayer.positionField.target.uuid)
                    assertTrue(dbField.disposedItems.isEmpty())
                    val playerfield = currentPlayer.positionField.target
                    assertTrue(playerfield.disposedItems.isEmpty())
                }
    }

//
//    @Test
//    fun activateItem_onRoundEnd_removeIt() {
//        val item = ItemInfo.EXTRA_FUEL()
//        TestEnvironment()
//                .setPlayerItems { listOf(item) }
//                .initGame()
//                .apply { assertTrue(currentPlayer.storageItems.isNotEmpty()) }
//                .activateItem(item) { equipResult ->
//                    equipResult.playerData.storageItems.isEmpty() &&
//                            equipResult.playerData.activeItems.find { it.id == equipResult.itemData.id }?.itemInfo?.charges?.let { it > 0 } ?: false
//                }.setToMovePhase()
//                .move()
//                .apply { move(target = circleStep1Field) }
//                .nextPhase()
//                .endRound()
//                .apply {
//                    assertTrue(currentPlayer.storageItems.isEmpty())
//                    assertTrue(currentPlayer.activeItems.isEmpty())
//                }
//    }
}