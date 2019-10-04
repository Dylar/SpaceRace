package de.bitb.spacerace.model.items

import de.bitb.spacerace.database.items.DiceAddition
import de.bitb.spacerace.model.items.disposable.SlowMine
import de.bitb.spacerace.model.items.disposable.moving.MovingMine
import de.bitb.spacerace.model.items.equip.IonEngine
import de.bitb.spacerace.model.items.ships.BumperShip
import de.bitb.spacerace.model.items.ships.RaiderShip
import de.bitb.spacerace.model.items.ships.SpeederShip
import de.bitb.spacerace.model.items.usable.ExtraFuel
import de.bitb.spacerace.model.items.usable.SpecialFuel
import de.bitb.spacerace.model.items.usable.SpeedBoost
import de.bitb.spacerace.model.items.usable.clean.CleanDroid
import de.bitb.spacerace.model.player.PlayerColor


sealed class ItemType(val price: Int) {

    //USABLE
    class EXTRA_FUEL : ItemType(2000), DiceAddition

    class SPECIAL_FUEL : ItemType(1000)
    class SPEED_BOOST : ItemType(3000)
    class CLEAN_DROID : ItemType(2000)

    //DISPOSABLE
    class SLOW_MINE : ItemType(3000)

    class MOVING_MINE : ItemType(4000)

    //EQUIP
    class ION_ENGINE : ItemType(5000)

    //SHIP
    class SHIP_SPEEDER : ItemType(15000)

    class SHIP_RAIDER : ItemType(65000)
    class SHIP_BUMPER : ItemType(40000)

    companion object {
        fun getAllItems(): MutableList<Item> = getAll().map { it.createGraphic() }.toMutableList()
        fun getAll() = ItemType::class.sealedSubclasses.mapNotNull { it.objectInstance }
        fun getRandomItem(playerColor: PlayerColor,
                          index: Int = (Math.random() * (getAll().size - 1)).toInt()
        ): Item = getAll()[index].createGraphic(playerColor)
    }

    fun createGraphic(playerColor: PlayerColor = PlayerColor.NONE): Item {
        return when (this) {
            is EXTRA_FUEL -> ExtraFuel(playerColor, 2000)
            is SPECIAL_FUEL -> SpecialFuel(playerColor, 1000)
            is SPEED_BOOST -> SpeedBoost(playerColor, 3000, 1)
            is CLEAN_DROID -> CleanDroid(playerColor, 2000)

            is SLOW_MINE -> SlowMine(playerColor, 3000)
            is MOVING_MINE -> MovingMine(playerColor, 4000)

            is ION_ENGINE -> IonEngine(playerColor, 5000)

            is SHIP_SPEEDER -> SpeederShip(playerColor, 15000)
            is SHIP_RAIDER -> RaiderShip(playerColor, 65000)
            is SHIP_BUMPER -> BumperShip(playerColor, 40000)
        }
    }

}