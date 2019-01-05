package de.bitb.spacerace.model.items.equip

import com.badlogic.gdx.graphics.Texture
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.player.PlayerColor

abstract class EquipItem(img: Texture, owner: PlayerColor, itemType: ItemCollection, text: String, price: Int) : Item(img, owner, itemType, text, price) {

    override fun canUse(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE, ItemState.EQUIPPED -> owner == player && getPlayerData(game).phase.isMain()
            else -> {
                return false
            }
        }
    }

    override fun use(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> {
                getPlayerData(game).playerItems.equipItem(this) //TODO only 4 engine bla
                return true
            }
            ItemState.EQUIPPED -> {
                getPlayerData(game).playerItems.unequipItem(this)
                return true
            }
            else -> false
        }
    }

}