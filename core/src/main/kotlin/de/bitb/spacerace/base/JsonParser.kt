package de.bitb.spacerace.base

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.bitb.spacerace.model.items.ItemType
import de.bitb.spacerace.model.items.ItemType.*

object JsonParser {

    val moshi: Moshi = Moshi.Builder()
            .add(PolymorphicJsonAdapterFactory.of(ItemType::class.java, "name")
                    .withSubtype(SHIP_BUMPER::class.java, SHIP_BUMPER::class.simpleName)
                    .withSubtype(SHIP_RAIDER::class.java, SHIP_RAIDER::class.simpleName)
                    .withSubtype(SHIP_SPEEDER::class.java, SHIP_SPEEDER::class.simpleName)
                    .withSubtype(MOVING_MINE::class.java, MOVING_MINE::class.simpleName)
                    .withSubtype(SLOW_MINE::class.java, SLOW_MINE::class.simpleName)
                    .withSubtype(CLEAN_DROID::class.java, CLEAN_DROID::class.simpleName)
                    .withSubtype(SPEED_BOOST::class.java, SPEED_BOOST::class.simpleName)
                    .withSubtype(SPECIAL_FUEL::class.java, SPECIAL_FUEL::class.simpleName)
                    .withSubtype(EXTRA_FUEL::class.java, EXTRA_FUEL::class.simpleName))
            .add(KotlinJsonAdapterFactory())
            .build()
    val itemTypeParser: JsonAdapter<ItemType> = moshi.adapter(ItemType::class.java)

}