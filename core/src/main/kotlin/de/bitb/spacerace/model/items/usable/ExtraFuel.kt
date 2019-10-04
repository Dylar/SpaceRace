package de.bitb.spacerace.model.items.usable

import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_EXTRA_FUEL_TEXT
import de.bitb.spacerace.database.items.DiceAddition
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemType
import de.bitb.spacerace.model.player.PlayerColor

class ExtraFuel(
        playerColor: PlayerColor,
        price: Int
) : UsableItem(playerColor, price, TextureCollection.speederShipMoving1),
        DiceAddition {
    override val itemType: ItemType = ItemType.EXTRA_FUEL()
    override var text: String = ""
        get() = ITEM_EXTRA_FUEL_TEXT

    override fun getAddition(): Int {
        return 2
    }
}