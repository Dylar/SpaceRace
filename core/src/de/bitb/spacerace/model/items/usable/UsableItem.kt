package de.bitb.spacerace.model.items.usable

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.items.itemtype.DiceAddition
import de.bitb.spacerace.model.player.PlayerColor

abstract class UsableItem(img: Texture, owner: PlayerColor, itemType: ItemCollection, text: String, price: Int, charges: Int = 1) : Item(img, owner, itemType, text, price, charges) {

    override fun canUse(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> getPlayerData(game, player).phase.isMain1()
            ItemState.USED -> getPlayerData(game, player).phase.isMain2()
            else -> false
        }
    }

    override fun use(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> {
                getPlayerData(game, player).playerItems.useItem(this)
                true
            }
            ItemState.USED -> {
                getPlayerData(game, player).playerItems.removeUsedItem(this)
                true
            }
            else -> true
        }
    }

}