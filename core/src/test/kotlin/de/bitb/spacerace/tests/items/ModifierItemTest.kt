package de.bitb.spacerace.tests.items

import de.bitb.spacerace.core.assertPlayerModi
import de.bitb.spacerace.grafik.model.items.ItemInfo
import org.junit.Test

class ModifierItemTest : ItemsTest() {

    @Test
    fun equipItem_addMods_ION_ENGINE() {
        val item = ItemInfo.EngineIonInfo()
        equipThatItem(item, true).assertPlayerModi(assertMod = item.diceModifier)
    }

}