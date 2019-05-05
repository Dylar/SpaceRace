package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.config.GOAL_CREDITS
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.gameover.GameOverCommand
import de.bitb.spacerace.model.player.PlayerColor
import org.greenrobot.eventbus.EventBus

class ObtainGoalCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.fieldController.currentGoal == getPlayerField(game, playerColor)
    }

    override fun execute(game: MainGame) {
        getPlayerData(game, playerColor).credits += GOAL_CREDITS
        val value: Int = game.gameController.playerController.victories[playerColor]!!
        game.gameController.playerController.victories[playerColor] = value + 1
        game.gameController.fieldController.setRandomGoal()

        val winner = game.gameController.playerController.getWinner()
        if (winner != PlayerColor.NONE) {
            EventBus.getDefault().post(GameOverCommand(winner))
        }
    }

}