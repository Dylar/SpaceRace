package de.bitb.spacerace.database.converter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.grafik.model.items.ItemInfo.*
import de.bitb.spacerace.grafik.model.items.ItemType

object JsonParser {

    private val moshi: Moshi = Moshi.Builder()
            .add(PolymorphicJsonAdapterFactory.of(ItemInfo::class.java, "type")
                    .withSubtype(ShipBumperInfo::class.java, ItemType.SHIP_BUMPER.name)
                    .withSubtype(ShipRaiderInfo::class.java, ItemType.SHIP_RAIDER.name)
                    .withSubtype(ShipSpeederInfo::class.java, ItemType.SHIP_SPEEDER.name)

                    .withSubtype(MineMovingInfo::class.java, ItemType.MINE_MOVING.name)
                    .withSubtype(MineSlowInfo::class.java, ItemType.MINE_SLOW.name)

                    .withSubtype(DroidCleanInfo::class.java, ItemType.DROID_CLEAN.name)

                    .withSubtype(FuelSpecialInfo::class.java, ItemType.FUEL_SPECIAL.name)
                    .withSubtype(FuelExtraInfo::class.java, ItemType.FUEL_EXTRA.name)
                    .withSubtype(BoostSpeedInfo::class.java, ItemType.BOOST_SPEED.name)

                    .withSubtype(EngineIonInfo::class.java, ItemType.ENGINE_ION.name)
            )
            .add(KotlinJsonAdapterFactory())
            .build()
    val ITEM_INFO_PARSER: JsonAdapter<ItemInfo> = moshi.adapter(ItemInfo::class.java)

}