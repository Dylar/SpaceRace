package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.maps.MapType
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.ui.screens.game.GameScreen

class StartGameCommand() : BaseCommand() {
    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.gamePlayer.size > 1
    }

    override fun execute(game: MainGame) {
        val gameController = game.gameController
        gameController.inputHandler.removeListener()
        gameController.initGame(SpaceMap.createMap(gameController, game.gameController.mapType))
        game.changeScreen(GameScreen(game))
    }
}