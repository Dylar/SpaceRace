package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.player.PlayerColor

class SellItemCommand(val item: Item, seller: PlayerColor) : BaseCommand(seller) {

    override fun canExecute(game: MainGame): Boolean {
        return getPlayerData(game, playerColor).playerItems.getSaleableItems(item.itemType).isNotEmpty()
    }

    override fun execute(game: MainGame) {
        val playerData = getPlayerData(game, playerColor)
        val item = playerData.playerItems.getItems(item.itemType)[0]
        playerData.playerItems.sellItem(item)
        playerData.credits += (item.price * 0.7).toInt()
    }

}