package de.bitb.spacerace.events.commands

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame

class DiceCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.playerController.canDice(playerColor)
    }

    override fun execute(game: MainGame) {
        game.gameController.playerController.dice()
    }

}