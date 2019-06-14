package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item

class UseItemCommand(val item: Item, playerData: PlayerData) : BaseCommand(playerData) {

    override fun canExecute(game: MainGame): Boolean {
        return item.canUse(game, playerData.playerColor)
    }

    override fun execute(game: MainGame) {
        item.use(game, playerData.playerColor)
    }

}