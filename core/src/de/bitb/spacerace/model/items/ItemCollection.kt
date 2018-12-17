package de.bitb.spacerace.model.items

import de.bitb.spacerace.model.items.upgrade.IonEngine
import de.bitb.spacerace.model.items.usable.ExtraFuel
import de.bitb.spacerace.model.items.usable.SpezialFuel

object ItemCollection {

    private const val ITEM1 = 0
    private const val ITEM2 = ITEM1 + 1
    private const val ITEM3 = ITEM2 + 1
    private const val ITEM_COUNT = ITEM3 + 1

    fun getRandomItem(index: Int = (Math.random() * ITEM_COUNT).toInt()): Item {
        return when (index) {
            ITEM1 -> ExtraFuel()
            ITEM2 -> SpezialFuel()
            ITEM3 -> IonEngine()
            else -> getRandomItem()
        }
    }


}