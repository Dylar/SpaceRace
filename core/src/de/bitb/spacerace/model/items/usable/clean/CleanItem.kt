package de.bitb.spacerace.model.items.usable.clean

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.usable.UsableItem
import de.bitb.spacerace.model.player.PlayerColor

abstract class CleanItem(playerColor: PlayerColor, price: Int) : UsableItem(playerColor, price) {

    val cleanable = ArrayList<ItemCollection>()

    override fun use(game: MainGame, player: PlayerColor): Boolean {
        if (state.isStorage()) {
            getPlayerData(game, player).playerItems.detachItems(cleanable)
        }
        return super.use(game, player)
    }


}