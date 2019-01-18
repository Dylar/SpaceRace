package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand

class ObtainGiftCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val lose = getPlayerData(game, playerColor).playerItems.addRandomGift()
    }

}