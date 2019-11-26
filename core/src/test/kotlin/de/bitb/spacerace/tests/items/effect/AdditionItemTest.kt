package de.bitb.spacerace.tests.items.effect

import de.bitb.spacerace.core.assertPlayerModi
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.tests.items.ItemsTest
import org.junit.Test

class AdditionItemTest : ItemsTest() {

    @Test
    fun equipItem_addValue_EXTRA_FUEL() {
        val item = ItemInfo.FuelExtraInfo()
        equipThatItem(item, true).assertPlayerModi(assertAdd = item.diceAddition)
    }

}