package de.bitb.spacerace.tests.items

import de.bitb.spacerace.core.assertPlayerModi
import de.bitb.spacerace.grafik.model.items.ItemInfo
import org.junit.Test

class AttachedItemTest : ItemsTest() {

    @Test
    fun attachItem_addValue_MINE_MOVING() {
        val item = ItemInfo.MineMovingInfo()
        attachThatItem(item, true).assertPlayerModi(assertAdd = item.diceAddition)
    }

}