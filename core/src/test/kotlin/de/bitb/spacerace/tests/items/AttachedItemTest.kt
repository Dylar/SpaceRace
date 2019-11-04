package de.bitb.spacerace.tests.items

import de.bitb.spacerace.core.assertDBPlayer
import de.bitb.spacerace.core.assertPlayerModi
import de.bitb.spacerace.env.TEST_PLAYER_1
import de.bitb.spacerace.env.endRound
import de.bitb.spacerace.grafik.model.items.ItemInfo
import org.junit.Test

class AttachedItemTest : ItemsTest() {

    @Test
    fun attachItem_addValue_MINE_MOVING() {
        val item = ItemInfo.MineMovingInfo()
        attachThatItem(item, true)
                .assertPlayerModi(assertAdd = item.diceAddition)
    }

    @Test
    fun attachItem_onRoundEnd_removeIt() {
        val item = ItemInfo.MineSlowInfo()
        attachThatItem(item, true)
                .assertDBPlayer(TEST_PLAYER_1) { it.attachedItems.first().itemInfo.charges == 2 }
                .endRound()
                .assertDBPlayer(TEST_PLAYER_1) { it.attachedItems.first().itemInfo.charges == 1 }
                .apply { endRound(leftBottomField) }
                .assertDBPlayer(TEST_PLAYER_1) { it.attachedItems.isEmpty() }
    }
}