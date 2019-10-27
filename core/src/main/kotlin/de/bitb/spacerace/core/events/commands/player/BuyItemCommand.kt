package de.bitb.spacerace.core.events.commands.player

import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.items.getDefaultInfo
import javax.inject.Inject

class BuyItemCommand(
        val itemInfo: ItemType,
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
        return DONT_USE_THIS_PLAYER_DATA.credits >= itemInfo.getDefaultInfo().price
    }

    override fun execute() {
        //TODO make usecase
        val itemData = ItemData(itemInfo = itemInfo.getDefaultInfo())
        DONT_USE_THIS_PLAYER_DATA.storageItems.add(itemData)
        DONT_USE_THIS_PLAYER_DATA.credits -= itemInfo.getDefaultInfo().price
        playerDataSource.insertRXPlayer(DONT_USE_THIS_PLAYER_DATA).subscribe()
    }

}