package de.bitb.spacerace.model.items

import de.bitb.spacerace.model.items.upgrade.IonEngine
import de.bitb.spacerace.model.items.usable.ExtraFuel
import de.bitb.spacerace.model.items.usable.SpecialFuel
import de.bitb.spacerace.model.player.PlayerColor

enum class ItemCollection {

    //USABLE
    EXTRA_FUEL,
    SPECIAL_FUEL,

    //UGRADES
    ION_ENGINE;

    companion object {
        fun getAllItems(): MutableList<Item> {
            val result = ArrayList<Item>()
            for (value in values()) {
                result.add(value.create())
            }
            return result
        }

        fun getRandomItem(playerColor: PlayerColor, index: Int = (Math.random() * values().size).toInt()): Item {
            return values()[index].create(playerColor)
        }
    }

    fun create(playerColor: PlayerColor = PlayerColor.NONE): Item {
        return when (this) {
            EXTRA_FUEL -> ExtraFuel(playerColor)
            SPECIAL_FUEL -> SpecialFuel(playerColor)
            ION_ENGINE -> IonEngine(playerColor)
        }
    }

}