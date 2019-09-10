package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item

class UseItemCommand(val item: Item, playerData: PlayerData) : BaseCommand(playerData) {

    override fun canExecute(): Boolean {
        return item.canUse(DONT_USE_THIS_PLAYER_DATA)
    }

    override fun execute() {
        item.use(DONT_USE_THIS_PLAYER_DATA)
    }

}