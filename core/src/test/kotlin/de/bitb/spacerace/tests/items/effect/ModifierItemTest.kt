package de.bitb.spacerace.tests.items.effect

import de.bitb.spacerace.core.assertPlayerModi
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.tests.items.ItemsTest
import org.junit.Test

class ModifierItemTest : ItemsTest() {

    @Test
    fun equipItem_addMods_ION_ENGINE() {
        val item = ItemInfo.EngineIonInfo()
        equipThatItem(item, true).assertPlayerModi(assertMod = item.diceModifier)
    }

}