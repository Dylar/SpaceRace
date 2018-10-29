package de.bitb.spacerace.model.items

import de.bitb.spacerace.core.TextureCollection

object ItemCollection {

    val allItems: MutableList<Item> = ArrayList()

    init {
        var item = Item(TextureCollection.blackhole)
        allItems.add(item)
        item = Item(TextureCollection.redField)
        allItems.add(item)
        item = Item(TextureCollection.fallingStar)
        allItems.add(item)
    }

    fun getRandomItem(): Item {
        return allItems[(Math.random() * allItems.size).toInt()]
    }

}