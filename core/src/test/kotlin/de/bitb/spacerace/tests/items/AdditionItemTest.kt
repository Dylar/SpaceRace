package de.bitb.spacerace.tests.items

import de.bitb.spacerace.core.assertPlayerModi
import de.bitb.spacerace.grafik.model.items.ItemInfo
import org.junit.Test

class AdditionItemTest : ItemsTest() {

    @Test
    fun equipItem_addValue_EXTRA_FUEL() {
        val item = ItemInfo.FuelExtraInfo()
        equipThatItem(item, true).assertPlayerModi(assertAdd = item.diceAddition)
    }

}