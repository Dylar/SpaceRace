package de.bitb.spacerace.core.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import javax.inject.Inject

class BuyItemCommand(
        val itemInfo: ItemType,
        buyer: PlayerColor
) : BaseCommand(buyer) {
    //TODO make get from pool

    @Inject
    protected lateinit var graphicController: GraphicController

    @Inject
    protected lateinit var playerDataSource: PlayerDataSource

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        //TODO make usecase
//        if(                return DONT_USE_THIS_PLAYER_DATA.credits >= itemInfo.getDefaultInfo().price)
//        val itemData = ItemData(itemInfo = itemInfo.getDefaultInfo())
//        DONT_USE_THIS_PLAYER_DATA.storageItems.add(itemData)
//        DONT_USE_THIS_PLAYER_DATA.credits -= itemInfo.getDefaultInfo().price
//        playerDataSource.insertRXPlayer(DONT_USE_THIS_PLAYER_DATA).subscribe()
    }

}