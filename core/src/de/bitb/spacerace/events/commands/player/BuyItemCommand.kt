package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.player.PlayerColor

class BuyItemCommand(val item: Item, buyer: PlayerData) : BaseCommand(buyer) {

    override fun canExecute(game: MainGame): Boolean {
        return playerData.credits >= item.price
    }

    override fun execute(game: MainGame) {
        getPlayerItems(game, playerData.playerColor).addItem(item.itemType.create(playerData.playerColor))
        playerData.credits -= item.price
    }

}