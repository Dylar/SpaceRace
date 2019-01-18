package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.config.GOAL_CREDITS
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.gameover.GameOverCommand

class ObtainGoalCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.currentGoal == getPlayerField(game, playerColor)
    }

    override fun execute(game: MainGame) {
        getPlayerData(game, playerColor).credits += GOAL_CREDITS
        val value: Int = game.gameController.victories[playerColor]!!
        game.gameController.victories[playerColor] = value + 1
        game.gameController.setRandomGoal()

        val winner = game.gameController.getWinner()
        if (winner != PlayerColor.NONE) {
            game.gameController.inputHandler.handleCommand(GameOverCommand(winner))
        }
    }

}