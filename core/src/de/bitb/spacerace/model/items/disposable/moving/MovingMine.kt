package de.bitb.spacerace.model.items.disposable.moving

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.config.strings.GameStrings
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.items.itemtype.DiceAddition
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.player.PlayerColor

class MovingMine(owner: PlayerColor, price: Int) : DisposableItem(owner, price), DiceAddition {

    override val itemType: ItemCollection = ItemCollection.SLOW_MINE
    override val img: Texture = TextureCollection.slowMine
    override var text: String = ""
        get() = GameStrings.ItemStrings.ITEM_MOVING_MINE_TEXT

    override fun getAddition(): Int {
        return -1
    }

}