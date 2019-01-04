package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.player.PlayerColor

class DiceCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        val playerData = getPlayerData(game)
        return playerData.phase.isMain1() &&
                playerData.playerColor == playerColor &&
                !playerData.diced
    }

    override fun execute(game: MainGame) {
        game.gameController.playerController.dice()
    }

}