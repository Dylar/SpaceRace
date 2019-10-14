package de.bitb.spacerace.model.items.usable

import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_SPECIAL_FUEL_TEXT
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor

class SpecialFuel(
        playerColor: PlayerColor,
        price: Int
) : UsableItemGraphic(playerColor, price, TextureCollection.speederShipMoving2) {

    override var itemInfo: ItemInfo = ItemInfo.SPECIAL_FUEL()
    override var text: String = ""
        get() = ITEM_SPECIAL_FUEL_TEXT

}