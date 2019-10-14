package de.bitb.spacerace.model.items.disposable.moving

import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor

class MovingMine(owner: PlayerColor, price: Int) : MovingItemGraphic(owner, price, TextureCollection.slowMine) {

   override var itemInfo: ItemInfo = ItemInfo.MOVING_MINE()
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_MOVING_MINE_TEXT

}