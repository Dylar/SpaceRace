package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.upgrade.UpgradeItem

class UseItemCommand(val item: Item) : BaseCommand(item.owner) {

    override fun canExecute(game: MainGame): Boolean {
        return item.canUse(game)
    }

    override fun execute(game: MainGame) {
        if (!item.used) {
            item.used = true
            item.use(game)
        } else if (item is UpgradeItem) {
            item.used = false
            item.stopUsing(game)
        }
    }

}