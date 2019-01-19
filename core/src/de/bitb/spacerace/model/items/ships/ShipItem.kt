package de.bitb.spacerace.model.items.ships

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.objecthandling.BaseAnimation
import de.bitb.spacerace.model.player.PlayerAnimation
import de.bitb.spacerace.model.player.PlayerColor

abstract class ShipItem(owner: PlayerColor, price: Int) : Item(owner, price) {
    abstract fun getAnimation(): BaseAnimation

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
                getPlayer(game, player).playerImage.animation = getAnimation()
                getPlayerItems(game, player).changeShip(this)
                return true
            }
//            ItemState.EQUIPPED -> {
//                getPlayerData(game, player).playerItems.unequipItem(this)
//                return true
//            }
            else -> false
        }
    }

}