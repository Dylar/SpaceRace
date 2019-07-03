package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item
import javax.inject.Inject

class BuyItemCommand(val item: Item, buyer: PlayerData) : BaseCommand(buyer) {

    @Inject
    protected lateinit var playerController: PlayerController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return playerData.credits >= item.price
    }

    override fun execute() {
        getPlayerItems(playerController, playerData.playerColor).addItem(item.itemType.create(playerData.playerColor))
        playerData.credits -= item.price
    }

}