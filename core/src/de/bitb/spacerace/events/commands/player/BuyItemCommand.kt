package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.player.PlayerColor

class BuyItemCommand(val item: Item, buyer: PlayerColor) : BaseCommand(buyer) {

    override fun canExecute(game: MainGame): Boolean {
        return getPlayerData(game, playerColor).credits >= item.price
    }

    override fun execute(game: MainGame) {
        getPlayerData(game, playerColor).playerItems.addItem(item.itemType.create(playerColor))
        getPlayerData(game, playerColor).credits -= item.price
    }

}