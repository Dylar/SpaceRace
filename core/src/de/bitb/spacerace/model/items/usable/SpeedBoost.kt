package de.bitb.spacerace.model.items.usable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_SPEED_BOOST_TEXT
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.itemtype.MultiDice
import de.bitb.spacerace.model.player.PlayerColor

class SpeedBoost(playerColor: PlayerColor, price: Int, val value: Int) : UsableItem(playerColor, price), MultiDice {

    override val itemType: ItemCollection = ItemCollection.SPEED_BOOST
    override val img: Texture = TextureCollection.fallingStar
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_SPEED_BOOST_TEXT

    override fun getAmount(): Int {
        return value
    }

}