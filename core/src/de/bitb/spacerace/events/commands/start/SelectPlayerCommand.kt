package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor

class SelectPlayerCommand(var playerColor: PlayerColor) : BaseCommand(Player.NONE.playerData) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        if (!game.gameController.gamePlayer.remove(playerColor)) {
            game.gameController.gamePlayer.add(playerColor)
        }
    }

}