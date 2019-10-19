package de.bitb.spacerace.tests.fields

import de.bitb.spacerace.env.*
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.items.ItemInfo.EXTRA_FUEL
import de.bitb.spacerace.model.items.ItemInfo.ION_ENGINE
import org.junit.Assert.assertTrue
import org.junit.Test

class ObtainGiftTest : ObtainFieldTest() {

    @Test
    fun obtainGift_receiveItemIntoStorage() {
        TestEnvironment()
                .obtainField(FieldType.GIFT) {
                    val player = it.player
                    val field = player.positionField.target

                    field.fieldType == FieldType.GIFT &&
                            player.storageItems.isNotEmpty()
                }
    }

    @Test
    fun setItem_obtainGift_receiveSetItemIntoStorage() {
        val item = ION_ENGINE()
        TestEnvironment()
                .setGiftFieldItems { listOf(item) }
                .obtainField(FieldType.GIFT) {
                    val player = it.player
                    val storageItem = player.storageItems.first()

                    storageItem.itemInfo.name == item.name
                }
    }

    @Test //maybe FLAKY
    fun setItemMultipleItems_obtainGift_receiveSetItemIntoStorage() {
        val items = listOf(ION_ENGINE(), EXTRA_FUEL())
        val giftedItems = mutableListOf<ItemInfo>()
        repeat(10) {
            TestEnvironment()
                    .setGiftFieldItems { items }
                    .obtainField(FieldType.GIFT) {
                        val player = it.player
                        val storageItem = player.storageItems.first()
                        giftedItems.add(storageItem.itemInfo)
                        true
                    }
        }

        assertTrue(giftedItems.any { it.name == items[0].name })
        assertTrue(giftedItems.any { it.name == items[1].name })
    }
}