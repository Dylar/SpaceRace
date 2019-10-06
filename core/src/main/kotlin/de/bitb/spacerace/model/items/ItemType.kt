package de.bitb.spacerace.model.items

import com.squareup.moshi.JsonClass
import de.bitb.spacerace.database.items.DiceAddition
import de.bitb.spacerace.database.items.DiceModification
import de.bitb.spacerace.database.items.MultiDice
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
import kotlin.reflect.full.createInstance


sealed class ItemType(
        val price: Int = 0
) {
    companion object {
        fun getAllItems(): MutableList<Item> = getAll().map { it.createGraphic() }.toMutableList()
        fun getAll() =
                ItemType::class.nestedClasses
                        .asSequence()
                        .filter { it.simpleName != "Companion" }
                        .map {
                            it.createInstance() as ItemType
                        }
                        .toList()

        fun getRandomItem(playerColor: PlayerColor,
                          index: Int = (Math.random() * (getAll().size - 1)).toInt()
        ): Item = getAll()[index].createGraphic(playerColor)
    }

    //USABLE
    @JsonClass(generateAdapter = true)
    class EXTRA_FUEL(
            override val diceAddition: Int = 2
    ) : ItemType(2000), DiceAddition

    @JsonClass(generateAdapter = true)
    class SPECIAL_FUEL(
            override val diceModifier: Double = 0.3
    ) : ItemType(1000), DiceModification

    @JsonClass(generateAdapter = true)
    class SPEED_BOOST(
            override val diceAmount: Int = 1
    ) : ItemType(3000), MultiDice

    class CLEAN_DROID() : ItemType(2000)

    //DISPOSABLE
    @JsonClass(generateAdapter = true)
    class SLOW_MINE(
            override val diceModifier: Double = -0.1
    ) : ItemType(3000), DiceModification

    @JsonClass(generateAdapter = true)
    class MOVING_MINE(
            override val diceAddition: Int = -1
    ) : ItemType(4000), DiceAddition

    //EQUIP
    @JsonClass(generateAdapter = true)
    class ION_ENGINE(
            override val diceModifier: Double = 0.1
    ) : ItemType(5000), DiceModification

    //SHIP
    @JsonClass(generateAdapter = true)
    class SHIP_SPEEDER(
            override val diceModifier: Double = -0.1
    ) : ItemType(15000), DiceModification

    @JsonClass(generateAdapter = true)
    class SHIP_RAIDER(
            override val diceAddition: Int = 3
    ) : ItemType(65000), DiceAddition

    @JsonClass(generateAdapter = true)
    class SHIP_BUMPER(
            override val diceModifier: Double = -0.1
    ) : ItemType(40000), DiceModification

    fun createGraphic(playerColor: PlayerColor = PlayerColor.NONE): Item {
        return when (this) {
            is EXTRA_FUEL -> ExtraFuel(playerColor, 2000)
            is SPECIAL_FUEL -> SpecialFuel(playerColor, 1000)
            is SPEED_BOOST -> SpeedBoost(playerColor, 3000)
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