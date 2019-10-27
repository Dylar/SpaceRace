package de.bitb.spacerace.tests.items

import de.bitb.spacerace.config.BITRISE_BORG
import de.bitb.spacerace.env.*
import de.bitb.spacerace.core.exceptions.ItemNotFoundException
import de.bitb.spacerace.grafik.model.items.ItemInfo
import org.junit.Assert.assertTrue
import org.junit.Test

class ActivateItemTest : ItemsTest() {

    @Test
    fun activateItem_storageEmpty_itemEquipped() {
        val item = ItemInfo.FuelExtraInfo()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply { assertTrue(getDBPlayer(currentPlayerColor).storageItems.isNotEmpty()) }
                .activateItem(item) { equipResult ->
                    equipResult.playerData.storageItems.isEmpty() &&
                            equipResult.playerData.activeItems.any { it.itemInfo.type == item.type }
                }
    }

    @Test
    fun activateItem_removeIt_itemInStorage_onlyEquipOnce() {
        val item = ItemInfo.FuelExtraInfo()
        TestEnvironment()
                .setPlayerItems { listOf(item) }
                .initGame()
                .apply { assertTrue(getDBPlayer(currentPlayerColor).storageItems.isNotEmpty()) }
                .activateItem(item) { equipResult ->
                    equipResult.playerData.storageItems.isEmpty() &&
                            equipResult.playerData.activeItems.any { it.itemInfo.type == item.type }
                }.activateItem(item, error = ItemNotFoundException(item.type))
    }

    @Test
    fun activateItem_onRoundEnd_removeIt() {
//        assertTrue(true) //TODO bitrise bug...
        if (BITRISE_BORG) {
            val item = ItemInfo.FuelExtraInfo()
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
}