package de.bitb.spacerace.model.items.usable.clean

import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.player.PlayerColor

class CleanDroid(playerColor: PlayerColor, price: Int) : CleanItem(playerColor, price,TextureCollection.unknownPlanet) {

    override val itemType: ItemCollection = ItemCollection.CLEAN_DROID
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_CLEAN_DROID_TEXT

    init {
        cleanable.add(ItemCollection.SLOW_MINE)
    }

}