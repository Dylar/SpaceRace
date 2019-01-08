package de.bitb.spacerace.model.items.disposable.moving

import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.player.PlayerColor

abstract class MovingItem(owner: PlayerColor, price: Int) : DisposableItem(owner, price) {

}