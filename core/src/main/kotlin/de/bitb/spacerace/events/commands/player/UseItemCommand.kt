package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item

class UseItemCommand(val item: Item, playerData: PlayerData) : BaseCommand(playerData) {

    override fun canExecute(): Boolean {
        return item.canUse(playerData)
    }

    override fun execute() {
        item.use(playerData)
    }

}