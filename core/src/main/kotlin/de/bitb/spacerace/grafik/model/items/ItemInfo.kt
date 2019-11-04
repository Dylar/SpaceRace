package de.bitb.spacerace.grafik.model.items

import com.squareup.moshi.JsonClass
import de.bitb.spacerace.config.DEBUG_GIFT_ITEMS
import de.bitb.spacerace.config.MOVING_SPS
import de.bitb.spacerace.config.strings.GameStrings.ItemStrings
import de.bitb.spacerace.database.items.*
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.grafik.model.items.ItemType.*
import de.bitb.spacerace.grafik.model.player.PlayerColor
import kotlin.reflect.full.createInstance

class NoneItem : ItemInfo(NONE_ITEM)

const val UNLIMITED_CHARGES = -1

sealed class ItemInfo(
        val type: ItemType,
        val price: Int = 0,
        var charges: Int = UNLIMITED_CHARGES,
        val usablePhase: Set<Phase> = setOf(Phase.MAIN1, Phase.MAIN2)
) {
    companion object {
        fun getAllItems(): MutableList<ItemGraphic> = getAll().map { it.createGraphic() }.toMutableList()
        fun getAll() =
                ItemInfo::class.nestedClasses
                        .asSequence()
                        .filter { it.simpleName != "Companion" }
                        .map { it.createInstance() as ItemInfo }
                        .toList()

        fun getRandomItem(): ItemInfo {
            val items =
                    if (DEBUG_GIFT_ITEMS.isEmpty()) getAll()
                    else DEBUG_GIFT_ITEMS
            return items[(Math.random() * (items.size)).toInt()]
        }
    }

    //USABLE
    @JsonClass(generateAdapter = true)
    class FuelExtraInfo(
            override val diceAddition: Int = 1
    ) : ItemInfo(FUEL_EXTRA, 2000, 1), ActivatableItem, DiceAddition

    @JsonClass(generateAdapter = true)
    class FuelSpecialInfo(
            override val diceModifier: Double = 0.3
    ) : ItemInfo(FUEL_SPECIAL, 1000, 1), ActivatableItem, DiceModification

    @JsonClass(generateAdapter = true)
    class BoostSpeedInfo(
            override val diceAmount: Int = 1
    ) : ItemInfo(BOOST_SPEED, 3000, 1), ActivatableItem, MultiDice

    @JsonClass(generateAdapter = true)
    class DroidCleanInfo(

    ) : ItemInfo(DROID_CLEAN, 2000, 1), ActivatableItem, RemoveEffect

    //DISPOSABLE
    @JsonClass(generateAdapter = true)
    class MineSlowInfo(
            override val diceModifier: Double = -0.1
    ) : ItemInfo(MINE_SLOW, 3000, 2), DisposableItem, DiceModification

    @JsonClass(generateAdapter = true)
    class MineMovingInfo(
            override val diceAddition: Int = -1
    ) : ItemInfo(MINE_MOVING, 4000, 3), DisposableItem, DiceAddition, MovableItem

    //EQUIP
    @JsonClass(generateAdapter = true)
    class EngineIonInfo(
            override val diceModifier: Double = 0.1,
            override var equipped: Boolean = false
    ) : ItemInfo(ENGINE_ION, 5000), EquipItem, DiceModification

    //SHIP
    @JsonClass(generateAdapter = true)
    class ShipSpeederInfo(
            override val diceModifier: Double = -0.1,
            override var speed: Float = MOVING_SPS * 1.4f
    ) : ItemInfo(SHIP_SPEEDER, 15000), DiceModification, Ship

    @JsonClass(generateAdapter = true)
    class ShipRaiderInfo(
            override val diceAddition: Int = 3,
            override var speed: Float = MOVING_SPS * 2.8f
    ) : ItemInfo(SHIP_RAIDER, 65000), DiceAddition, Ship

    @JsonClass(generateAdapter = true)
    class ShipBumperInfo(
            override val diceModifier: Double = -0.1,
            override var speed: Float = MOVING_SPS * 0.7f
    ) : ItemInfo(SHIP_BUMPER, 40000), DiceModification, Ship
}

enum class ItemType {
    NONE_ITEM,

    FUEL_EXTRA,
    FUEL_SPECIAL,
    BOOST_SPEED,

    DROID_CLEAN,

    MINE_SLOW,
    MINE_MOVING,

    ENGINE_ION,

    SHIP_SPEEDER,
    SHIP_RAIDER,
    SHIP_BUMPER;

}

fun ItemType.getDefaultInfo(): ItemInfo =
        when (this) {
            NONE_ITEM -> NoneItem()
            FUEL_EXTRA -> ItemInfo.FuelExtraInfo()
            FUEL_SPECIAL -> ItemInfo.FuelSpecialInfo()
            BOOST_SPEED -> ItemInfo.BoostSpeedInfo()
            DROID_CLEAN -> ItemInfo.DroidCleanInfo()
            MINE_SLOW -> ItemInfo.MineSlowInfo()
            MINE_MOVING -> ItemInfo.MineMovingInfo()
            ENGINE_ION -> ItemInfo.EngineIonInfo()
            SHIP_SPEEDER -> ItemInfo.ShipSpeederInfo()
            SHIP_RAIDER -> ItemInfo.ShipRaiderInfo()
            SHIP_BUMPER -> ItemInfo.ShipBumperInfo()
        }

fun ItemInfo.createGraphic(playerColor: PlayerColor = PlayerColor.NONE): ItemGraphic =
        type.createGraphic(playerColor)

fun ItemType.createGraphic(playerColor: PlayerColor = PlayerColor.NONE): ItemGraphic {
    return when (this) {
        NONE_ITEM -> throw UnsupportedOperationException("NONE ITEM TYPE!!")
        FUEL_EXTRA -> ItemGraphic(playerColor, this, TextureCollection.speederShipMoving3)
        FUEL_SPECIAL -> ItemGraphic(playerColor, this, TextureCollection.bumperShipMoving3)
        BOOST_SPEED -> ItemGraphic(playerColor, this, TextureCollection.raiderShipMoving3)
        DROID_CLEAN -> ItemGraphic(playerColor, this, TextureCollection.unknownPlanet)
        MINE_SLOW -> ItemGraphic(playerColor, this, TextureCollection.slowMine)
        MINE_MOVING -> ItemGraphic(playerColor, this, TextureCollection.slowMine)
        ENGINE_ION -> ItemGraphic(playerColor, this, TextureCollection.blackhole)
        SHIP_SPEEDER -> ItemGraphic(playerColor, this, this.getAnimation().getDefaultImage()!!.texture)
        SHIP_RAIDER -> ItemGraphic(playerColor, this, this.getAnimation().getDefaultImage()!!.texture)
        SHIP_BUMPER -> ItemGraphic(playerColor, this, this.getAnimation().getDefaultImage()!!.texture)
    }
}

fun ItemType.getAnimation() = when (this) {
    SHIP_RAIDER -> TextureCollection.raiderAnimation
    SHIP_BUMPER -> TextureCollection.bumperAnimation
    else -> TextureCollection.speederAnimation
}

fun ItemType.getText(): String {
    return when (this) {
        NONE_ITEM -> throw UnsupportedOperationException("NONE ITEM TYPE!!")
        FUEL_EXTRA -> ItemStrings.ITEM_EXTRA_FUEL_TEXT
        FUEL_SPECIAL -> ItemStrings.ITEM_SPECIAL_FUEL_TEXT
        BOOST_SPEED -> ItemStrings.ITEM_SPEED_BOOST_TEXT
        DROID_CLEAN -> ItemStrings.ITEM_CLEAN_DROID_TEXT
        MINE_SLOW -> ItemStrings.ITEM_SLOW_MINE_TEXT
        MINE_MOVING -> ItemStrings.ITEM_MOVING_MINE_TEXT
        ENGINE_ION -> ItemStrings.ITEM_ION_ENGINE_TEXT
        SHIP_SPEEDER -> ItemStrings.SHIP_SPEEDER_TEXT
        SHIP_RAIDER -> ItemStrings.SHIP_RAIDER_TEXT
        SHIP_BUMPER -> ItemStrings.SHIP_BUMPER_TEXT
    }
}


