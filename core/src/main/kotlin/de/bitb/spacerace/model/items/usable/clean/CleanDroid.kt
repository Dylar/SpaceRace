package de.bitb.spacerace.model.items.usable.clean

import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor

class CleanDroid(playerColor: PlayerColor, price: Int) : CleanItemGraphic(playerColor, price, TextureCollection.unknownPlanet) {

    override val itemInfo: ItemInfo = ItemInfo.CLEAN_DROID()
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_CLEAN_DROID_TEXT

    init {
        cleanable.add(ItemInfo.SLOW_MINE())
    }

}