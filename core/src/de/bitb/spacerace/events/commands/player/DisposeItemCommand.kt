package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.disposable.DisposableItem
import de.bitb.spacerace.model.items.upgrade.UpgradeItem

class DisposeItemCommand(val item: DisposableItem) : BaseCommand(item.owner) {

    override fun canExecute(game: MainGame): Boolean {
        return item.canUse(game)
    }

    override fun execute(game: MainGame) {
        if (!item.disposed) {
            item.disposed = true
            item.dispose(game)
        }
    }

}