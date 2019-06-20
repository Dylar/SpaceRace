package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.disposable.DisposableItem

class ObtainAmbushCommand(playerData: PlayerData) : BaseCommand(playerData) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val lose = getPlayerItems(game.gameController.playerController, playerData.playerColor)
                .attachItem(ItemCollection.SLOW_MINE.create(playerData.playerColor) as DisposableItem)
    }

}