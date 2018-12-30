package de.bitb.spacerace.model.items.disposable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.player.PlayerColor

abstract class DisposableItem(img: Texture, itemType: ItemCollection, owner: PlayerColor, text: String, price: Int) : Item(img, owner, itemType, text, price) {

    abstract fun dispose(game: MainGame)
}