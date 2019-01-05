package de.bitb.spacerace.model.items.disposable

import de.bitb.spacerace.config.strings.GameStrings.ItemStrings.ITEM_SLOW_MINE_TEXT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.player.PlayerColor

class SlowMine(owner: PlayerColor, price: Int) : DisposableItem(TextureCollection.slowMine, owner, ItemCollection.SLOW_MINE, ITEM_SLOW_MINE_TEXT, price), DiceModification {

    override fun getModification(): Float {
        return -0.1f
    }

}