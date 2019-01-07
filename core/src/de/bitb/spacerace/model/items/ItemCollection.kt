package de.bitb.spacerace.model.items

import de.bitb.spacerace.model.items.disposable.SlowMine
import de.bitb.spacerace.model.items.equip.IonEngine
import de.bitb.spacerace.model.items.usable.ExtraFuel
import de.bitb.spacerace.model.items.usable.clean.CleanDroid
import de.bitb.spacerace.model.items.usable.SpecialFuel
import de.bitb.spacerace.model.items.usable.SpeedBoost
import de.bitb.spacerace.model.player.PlayerColor
import javax.naming.OperationNotSupportedException

enum class ItemCollection {

    //USABLE
    EXTRA_FUEL,
    SPECIAL_FUEL,
    SPEED_BOOST,
    CLEAN_DROID,

    //EQUIP
    ION_ENGINE,

    //DISPOSABLE
    SLOW_MINE,
    MOVING_MINE,

    NONE;

    companion object {
        fun getAllItems(): MutableList<Item> {
            val result = ArrayList<Item>()
            for (value in values()) {
                if (value != NONE) {
                    result.add(value.create())
                }
            }
            return result
        }

        fun getRandomItem(playerColor: PlayerColor, index: Int = (Math.random() * (values().size - 1)).toInt()): Item {
            return values()[index].create(playerColor)
        }
    }

    fun create(playerColor: PlayerColor = PlayerColor.NONE): Item {
        return when (this) {
            EXTRA_FUEL -> ExtraFuel(playerColor, 2000)
            SPECIAL_FUEL -> SpecialFuel(playerColor, 1000)
            ION_ENGINE -> IonEngine(playerColor, 5000)
            SLOW_MINE -> SlowMine(playerColor, 3000)
            MOVING_MINE -> SlowMine(playerColor, 3000)
            SPEED_BOOST -> SpeedBoost(playerColor, 3000, 1)
            CLEAN_DROID -> CleanDroid(playerColor, 2000)

            NONE -> throw OperationNotSupportedException()
        }
    }

}