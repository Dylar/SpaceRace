package de.bitb.spacerace.model.items.usable

import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_EXTRA_FUEL_TEXT
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor

class ExtraFuel(
        playerColor: PlayerColor,
        price: Int
) : UsableItemGraphic(playerColor, price, TextureCollection.speederShipMoving1) {
    override var itemInfo: ItemInfo = ItemInfo.EXTRA_FUEL()
    override var text: String = ""
        get() = ITEM_EXTRA_FUEL_TEXT

}