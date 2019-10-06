package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.ItemType
import javax.inject.Inject

class BuyItemCommand(
        val itemType: ItemType,
        buyer: PlayerData
) : BaseCommand(buyer) {

    @Inject
    protected lateinit var graphicController: GraphicController

    @Inject
    protected lateinit var playerDataSource: PlayerDataSource

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return DONT_USE_THIS_PLAYER_DATA.credits >= itemType.price
    }

    override fun execute() {
        //TODO make usecase
        val itemData = ItemData(itemType = itemType)
        DONT_USE_THIS_PLAYER_DATA.storageItems.add(itemData)
        DONT_USE_THIS_PLAYER_DATA.credits -= itemType.price
        playerDataSource.insert(DONT_USE_THIS_PLAYER_DATA)
    }

}