package de.bitb.spacerace.events.commands.player

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand

class DiceCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.playerController.canDice(playerColor)
    }

    override fun execute(game: MainGame) {
        game.gameController.playerController.dice()
    }

}