package de.bitb.spacerace.tests.fields

import de.bitb.spacerace.config.DEBUG_ITEM
import de.bitb.spacerace.core.GameTest
import de.bitb.spacerace.env.*
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.ItemInfo.ION_ENGINE
import org.junit.Test

class ObtainGiftTest : GameTest() {

    @Test
    fun obtainGift_receiveItemIntoStorage() {
        TestEnvironment()
                .initGame(
                        map = createMap(firstStep = FieldType.GIFT)
                ).setToMovePhase()
                .move()
                .nextPhase {
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
                .also { DEBUG_ITEM = arrayListOf(item) }
                .initGame(
                        map = createMap(firstStep = FieldType.GIFT)
                ).setToMovePhase()
                .move()
                .nextPhase {
                    val player = it.player
                    val storageItem = player.storageItems.first()

                    storageItem.itemInfo.name == item.name
                }
    }
}