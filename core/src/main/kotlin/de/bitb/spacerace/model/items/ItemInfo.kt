package de.bitb.spacerace.model.items

import com.squareup.moshi.JsonClass
import de.bitb.spacerace.database.items.*
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

class NONE_ITEMTYPE() : ItemInfo(NONE_ITEMTYPE::class.simpleName!!, 0)

sealed class ItemInfo(
        val name: String,
        val price: Int = 0
) {
    companion object {
        fun getAllItems(): MutableList<ItemGraphic> = getAll().map { it.createGraphic() }.toMutableList()
        fun getAll() =
                ItemInfo::class.nestedClasses
                        .asSequence()
                        .filter { it.simpleName != "Companion" }
                        .map { it.createInstance() as ItemInfo }
                        .toList()

        fun getRandomItem() =
                getAll().let {
                    it[(Math.random() * (it.size - 1)).toInt()]
                }
    }

    //USABLE
    @JsonClass(generateAdapter = true)
    class EXTRA_FUEL(
            override val diceAddition: Int = 2
    ) : ItemInfo(EXTRA_FUEL::class.simpleName!!, 2000), ActivableItem, DiceAddition

    @JsonClass(generateAdapter = true)
    class SPECIAL_FUEL(
            override val diceModifier: Double = 0.3
    ) : ItemInfo(SPECIAL_FUEL::class.simpleName!!, 1000), ActivableItem, DiceModification

    @JsonClass(generateAdapter = true)
    class SPEED_BOOST(
            override val diceAmount: Int = 1
    ) : ItemInfo(SPEED_BOOST::class.simpleName!!, 3000), ActivableItem, MultiDice

    @JsonClass(generateAdapter = true)
    class CLEAN_DROID(

    ) : ItemInfo(CLEAN_DROID::class.simpleName!!, 2000), ActivableItem, RemoveEffect

    //DISPOSABLE
    @JsonClass(generateAdapter = true)
    class SLOW_MINE(
            override val diceModifier: Double = -0.1
    ) : ItemInfo(SLOW_MINE::class.simpleName!!, 3000), DisposableItem, DiceModification

    @JsonClass(generateAdapter = true)
    class MOVING_MINE(
            override val diceAddition: Int = -1
    ) : ItemInfo(MOVING_MINE::class.simpleName!!, 4000), DisposableItem, DiceAddition

    //EQUIP
    @JsonClass(generateAdapter = true)
    class ION_ENGINE(
            override val diceModifier: Double = 0.1
    ) : ItemInfo(ION_ENGINE::class.simpleName!!, 5000), EquipItem, DiceModification

    //SHIP
    @JsonClass(generateAdapter = true)
    class SHIP_SPEEDER(
            override val diceModifier: Double = -0.1
    ) : ItemInfo(SHIP_SPEEDER::class.simpleName!!, 15000), EquipItem, DiceModification

    @JsonClass(generateAdapter = true)
    class SHIP_RAIDER(
            override val diceAddition: Int = 3
    ) : ItemInfo(SHIP_RAIDER::class.simpleName!!, 65000), EquipItem, DiceAddition

    @JsonClass(generateAdapter = true)
    class SHIP_BUMPER(
            override val diceModifier: Double = -0.1
    ) : ItemInfo(SHIP_BUMPER::class.simpleName!!, 40000), EquipItem, DiceModification

    fun createGraphic(playerColor: PlayerColor = PlayerColor.NONE): ItemGraphic {
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
            is NONE_ITEMTYPE -> throw UnsupportedOperationException("NONE ITEM TYPE!!")
        }
    }

}