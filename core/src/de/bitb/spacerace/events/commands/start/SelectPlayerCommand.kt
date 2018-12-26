package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand

class SelectPlayerCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        if (!game.gameController.gamePlayer.remove(playerColor)) {
            game.gameController.gamePlayer.add(playerColor)
        }
    }

}