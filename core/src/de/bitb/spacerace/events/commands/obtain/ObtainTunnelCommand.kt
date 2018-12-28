package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand

class ObtainTunnelCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val gameController = game.gameController
        val playerController = gameController.playerController
        playerController.getPlayer(playerColor).setFieldPosition(gameController.fieldController.getRandomTunnel(playerColor))
    }

}