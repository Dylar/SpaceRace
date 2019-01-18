package de.bitb.spacerace.model.items.usable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_SPECIAL_FUEL_TEXT
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.player.PlayerColor

class SpecialFuel(playerColor: PlayerColor, price: Int) : UsableItem( playerColor, price), DiceModification {

    override val itemType: ItemCollection = ItemCollection.SPECIAL_FUEL
    override val img: Texture = TextureCollection.shipMoving2
    override var text: String = ""
        get() = ITEM_SPECIAL_FUEL_TEXT

    override fun getModification(): Float {
        return 0.3f
    }
}