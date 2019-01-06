package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame

class EndTurnCommand(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val playerController = game.gameController.playerController
        playerController.nextTurn()
        if (playerController.isRoundEnd()) {
            game.gameController.inputHandler.handleCommand(EndRoundCommand())
        }
    }

}