package de.bitb.spacerace.tests.items

import de.bitb.spacerace.core.GameTest
import de.bitb.spacerace.core.assertPlayerModi
import de.bitb.spacerace.env.TestEnvironment
import de.bitb.spacerace.env.equipItem
import de.bitb.spacerace.env.initGame
import de.bitb.spacerace.env.setPlayerItems
import de.bitb.spacerace.model.items.ItemInfo

open class ItemsTest : GameTest() {

    open fun equipThatItem(item: ItemInfo, equip: Boolean) =
            TestEnvironment()
                    .setPlayerItems { listOf(item) }
                    .initGame()
                    .assertPlayerModi()
                    .equipItem(item, equip = equip) { equipResult ->
                        equipResult.playerData.storageItems.isEmpty() &&
                                equipResult.playerData.equippedItems.any { it.itemInfo.type == item.type }
                    }
}