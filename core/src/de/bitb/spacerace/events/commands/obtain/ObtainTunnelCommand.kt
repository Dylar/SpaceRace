package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand

class ObtainTunnelCommand(playerData: PlayerData) : BaseCommand(playerData) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val gameController = game.gameController
        val tunnel = gameController.fieldController.getRandomTunnel(game, playerData.playerColor)
        //TODO klappt das? Nö :P grafik muss neu gesetzt werden.............
        getPlayer(game, playerData.playerColor).setFieldPosition(tunnel)
    }

}