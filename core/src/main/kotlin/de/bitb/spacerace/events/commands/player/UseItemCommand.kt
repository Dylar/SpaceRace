package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.ItemType

class UseItemCommand(
        private val item: ItemType,
        playerData: PlayerData
) : BaseCommand(playerData) {

    //TODO

    override fun canExecute(): Boolean {
        return true
//        return item.canUse(DONT_USE_THIS_PLAYER_DATA)
    }

    override fun execute() {
//        item.use(DONT_USE_THIS_PLAYER_DATA)
    }

}