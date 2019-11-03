package de.bitb.spacerace.tests.items

import de.bitb.spacerace.core.GameTest
import de.bitb.spacerace.core.assertPlayerModi
import de.bitb.spacerace.database.items.DiceAddition
import de.bitb.spacerace.database.items.DiceModification
import de.bitb.spacerace.env.*
import de.bitb.spacerace.grafik.model.items.ItemInfo

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

    open fun attachThatItem(item: ItemInfo, equip: Boolean) =
            TestEnvironment()
                    .initGame()
                    .assertPlayerModi()
                    .attachItem(TEST_PLAYER_2, item, TEST_PLAYER_1)
}