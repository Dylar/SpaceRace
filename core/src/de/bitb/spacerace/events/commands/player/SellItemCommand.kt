package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item

class SellItemCommand(val item: Item, seller: PlayerData) : BaseCommand(seller) {

    override fun canExecute(game: MainGame): Boolean {
        return getPlayerItems(game.gameController.playerController, playerData.playerColor).getSaleableItems(item.itemType).isNotEmpty()
    }

    override fun execute(game: MainGame) {
        getPlayerItems(game.gameController.playerController, playerData.playerColor)
                .apply {
                    val item = getItems(item.itemType)[0]
                    sellItem(item)
                    playerData.credits += (item.price * 0.7).toInt() //TODO modifier
                }

    }

}