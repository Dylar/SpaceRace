package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.ui.screens.game.GameScreen

class StartGameCommand() : BaseCommand() {
    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.gamePlayer.size > 1
    }

    override fun execute(game: MainGame) {
        val gameController = game.gameController
        gameController.inputHandler.removeListener()
        gameController.initGame(game.gameController.spaceMap.createMap(game.gameController))
        game.changeScreen(GameScreen(game))
    }
}