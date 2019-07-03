package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item
import javax.inject.Inject

class SellItemCommand(val item: Item, seller: PlayerData) : BaseCommand(seller) {

    @Inject
    protected lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return getPlayerItems(playerController, playerData.playerColor).getSaleableItems(item.itemType).isNotEmpty()
    }

    override fun execute() {
        getPlayerItems(playerController, playerData.playerColor)
                .apply {
                    val item = getItems(item.itemType)[0]
                    sellItem(item)
                    playerData.credits += (item.price * 0.7).toInt() //TODO modifier into constants
                }

    }

}