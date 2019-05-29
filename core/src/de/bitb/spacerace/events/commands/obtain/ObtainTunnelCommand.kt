package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.PlayerColor

class ObtainTunnelCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val gameController = game.gameController
        val tunnel = gameController.fieldController.getRandomTunnel(game, playerColor)
        //TODO klappt das? Nö :P grafik muss neu gesetzt werden.............
        getPlayer(game, playerColor).setFieldPosition(tunnel)
    }

}