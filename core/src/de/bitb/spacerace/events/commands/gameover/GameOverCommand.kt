package de.bitb.spacerace.events.commands.gameover

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.ui.screens.GameOverScreen

class GameOverCommand(val winner: PlayerColor = PlayerColor.NONE) : BaseCommand() {
    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        game.gameController.inputHandler.removeListener()
        game.changeScreen(GameOverScreen(game))
    }
}