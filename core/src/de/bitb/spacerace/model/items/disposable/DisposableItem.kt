package de.bitb.spacerace.model.items.disposable

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemState
import de.bitb.spacerace.model.player.PlayerColor

abstract class DisposableItem(owner: PlayerColor, price: Int) : Item(owner, price) {

    var attachedTo = PlayerColor.NONE

    override fun canUse(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> getPlayerData(game, player).phase.isMain()
            ItemState.DISPOSED, ItemState.USED -> getPlayerData(game, player).phase.isMain2()
            else -> false
        }
    }

    override fun use(game: MainGame, player: PlayerColor): Boolean {
        return when (state) {
            ItemState.STORAGE -> {
                getPlayerData(game, player).fieldPosition.disposeItem(this)
                getPlayerData(game, player).playerItems.disposeItem(this)
                true
            }
            ItemState.DISPOSED -> {
                attachedTo = player
                getPlayerData(game, player).playerItems.attachItem(this)
                getPlayerData(game, player).fieldPosition.attachItem(this)
                true
            }
            else -> true
        }
    }

}