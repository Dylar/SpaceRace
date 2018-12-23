package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.control.GameController

class ObtainWinCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val lose = game.gameController.playerController.getPlayer(playerColor).addRandomWin()
    }

}