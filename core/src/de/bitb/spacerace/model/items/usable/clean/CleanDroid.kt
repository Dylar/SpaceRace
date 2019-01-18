package de.bitb.spacerace.model.items.usable.clean

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.usable.UsableItem
import de.bitb.spacerace.model.player.PlayerColor

class CleanDroid(playerColor: PlayerColor, price: Int) : CleanItem(playerColor, price) {

    override val itemType: ItemCollection = ItemCollection.CLEAN_DROID
    override val img: Texture = TextureCollection.unknownPlanet
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_CLEAN_DROID_TEXT

    init {
        cleanable.add(ItemCollection.SLOW_MINE)
    }

}