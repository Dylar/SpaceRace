package de.bitb.spacerace.model.items.disposable

import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor

class SlowMine(owner: PlayerColor, price: Int) : DisposableItemGraphic(owner, price, TextureCollection.slowMine) {

    override val itemInfo: ItemInfo = ItemInfo.SLOW_MINE()
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_SLOW_MINE_TEXT

}