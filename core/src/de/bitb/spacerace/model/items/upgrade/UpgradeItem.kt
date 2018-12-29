package de.bitb.spacerace.model.items.upgrade

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.player.PlayerColor

abstract class UpgradeItem(img: Texture, itemType: ItemCollection, owner: PlayerColor, text: String, price: Int) : Item(img, owner, itemType, text, price) {

    abstract fun stopUsing(game: MainGame)
}