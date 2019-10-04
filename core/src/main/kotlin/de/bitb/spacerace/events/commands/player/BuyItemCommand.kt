package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item
import javax.inject.Inject

class BuyItemCommand(
        val item: Item,
        buyer: PlayerData
) : BaseCommand(buyer) {

    @Inject
    protected lateinit var graphicController: GraphicController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return DONT_USE_THIS_PLAYER_DATA.credits >= item.price
    }

    override fun execute() {
        //TODO items
//        graphicController
//                .getPlayerItems(DONT_USE_THIS_PLAYER_DATA.playerColor)
//                .addItem(item.itemType.create(DONT_USE_THIS_PLAYER_DATA.playerColor))
//        DONT_USE_THIS_PLAYER_DATA.credits -= item.price
    }

}