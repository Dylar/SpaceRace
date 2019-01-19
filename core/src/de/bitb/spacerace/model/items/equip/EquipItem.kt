package de.bitb.spacerace.model.items.equip

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class EquipItem(owner: PlayerColor, price: Int) : Item(owner, price) {

    override fun canUse(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE, ItemState.EQUIPPED -> owner == player && getPlayerData(game, player).phase.isMain()
            else -> {
                return false
            }
        }
    }

    override fun use(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> {
                getPlayerItems(game, player).equipItem(this)
                return true
            }
            ItemState.EQUIPPED -> {
                getPlayerItems(game, player).unequipItem(this)
                return true
            }
            else -> false
        }
    }

}