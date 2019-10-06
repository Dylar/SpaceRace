package de.bitb.spacerace.base

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import de.bitb.spacerace.model.items.ItemType

object JsonParser {

    val moshi: Moshi = Moshi.Builder().build()
    val itemTypeParser: JsonAdapter<ItemType> = moshi.adapter(ItemType::class.java)

}