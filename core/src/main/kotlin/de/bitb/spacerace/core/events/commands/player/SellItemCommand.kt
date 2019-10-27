package de.bitb.spacerace.core.events.commands.player

import de.bitb.spacerace.config.ITEM_SELL_MOD
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.core.events.commands.BaseCommand
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.items.getDefaultInfo
import javax.inject.Inject

class SellItemCommand(
        private val itemInfo: ItemType,
        seller: PlayerData
) : BaseCommand(seller) {

    @Inject
    protected lateinit var graphicController: GraphicController

    @Inject
    protected lateinit var playerDataSource: PlayerDataSource

    private val item = DONT_USE_THIS_PLAYER_DATA.storageItems.find { it::class == itemInfo::class }

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return item != null
    }

    override fun execute() {
        //TODO make usecase
        DONT_USE_THIS_PLAYER_DATA.storageItems.remove(item!!)
        DONT_USE_THIS_PLAYER_DATA.credits += (itemInfo.getDefaultInfo().price * ITEM_SELL_MOD).toInt()
        playerDataSource.insertRXPlayer(DONT_USE_THIS_PLAYER_DATA).subscribe()
    }

}