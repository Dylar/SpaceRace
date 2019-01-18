package de.bitb.spacerace.model.items.usable

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class UsableItem(owner: PlayerColor, price: Int) : Item(owner, price) {

    override fun canUse(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> getPlayerData(game, player).phase.isMain()
            ItemState.USED -> getPlayerData(game, player).phase.isMain2()//TODO muss das?
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