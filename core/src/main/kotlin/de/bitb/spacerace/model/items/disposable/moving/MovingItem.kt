package de.bitb.spacerace.model.items.disposable.moving

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.player.PlayerColor

abstract class MovingItem(owner: PlayerColor, price: Int, img: Texture) : DisposableItem(owner, price, img) {

}