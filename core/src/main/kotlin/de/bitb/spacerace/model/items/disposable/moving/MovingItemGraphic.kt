package de.bitb.spacerace.model.items.disposable.moving

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.model.items.disposable.DisposableItemGraphic
import de.bitb.spacerace.model.player.PlayerColor

abstract class MovingItemGraphic(owner: PlayerColor, price: Int, img: Texture) : DisposableItemGraphic(owner, price, img) {

}