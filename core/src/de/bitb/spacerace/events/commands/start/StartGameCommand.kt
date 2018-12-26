package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand

class StartGameCommand() : BaseCommand() {
    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.gamePlayer.size > 1
    }

    override fun execute(game: MainGame) {

    }


}