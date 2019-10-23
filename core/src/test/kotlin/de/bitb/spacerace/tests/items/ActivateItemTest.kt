package de.bitb.spacerace.tests.items

import de.bitb.spacerace.env.*
import de.bitb.spacerace.exceptions.ItemNotFoundException
import de.bitb.spacerace.model.items.ItemInfo
import org.junit.Assert.assertTrue
import org.junit.Test

class ActivateItemTest : ItemsTest() {

    @Test
    fun activateItem_storageEmpty_itemEquipped() {
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
    fun activateItem_removeIt_itemInStorage_onlyEquipOnce() {
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

    @Test
    fun activateItem_onRoundEnd_removeIt() {
        val item = ItemInfo.EXTRA_FUEL()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply { assertTrue(currentPlayer.storageItems.isNotEmpty()) }
                .activateItem(item) { equipResult ->
                    equipResult.playerData.storageItems.isEmpty() &&
                            equipResult.playerData.activeItems.find { it.id == equipResult.itemData.id }?.itemInfo?.charges?.let { it > 0 } ?: false
                }.setToMovePhase()
                .move()
                .apply { move(target = circleStep1Field) }
                .nextPhase()
                .endRound()
                .apply {
                    assertTrue(currentPlayer.storageItems.isEmpty())
                    assertTrue(currentPlayer.activeItems.isEmpty())
                }
    }
}