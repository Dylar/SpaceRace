package de.bitb.spacerace.model.items.usable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_EXTRA_FUEL_TEXT
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.itemtype.DiceAddition
import de.bitb.spacerace.model.player.PlayerColor

class ExtraFuel(playerColor: PlayerColor, price: Int) : UsableItem(playerColor, price), DiceAddition {
    override val itemType: ItemCollection = ItemCollection.EXTRA_FUEL
    override val img: Texture = TextureCollection.speederShipMoving1
    override var text: String = ""
        get() = ITEM_EXTRA_FUEL_TEXT

    override fun getAddition(): Int {
        return 2
    }
}