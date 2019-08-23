package de.bitb.spacerace.model.items.disposable

import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.grafik.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.player.PlayerColor

class SlowMine(owner: PlayerColor, price: Int) : DisposableItem(owner, price, TextureCollection.slowMine), DiceModification {

    override val itemType: ItemCollection = ItemCollection.SLOW_MINE
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_SLOW_MINE_TEXT

    override fun getModification(): Float {
        return -0.1f
    }

}