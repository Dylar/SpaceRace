package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame

class EndTurnCommand(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        val gameController = game.gameController
        return gameController.playerController.currentPlayer.playerData.phase.isEndTurn()
    }

    override fun execute(game: MainGame) {
        val gameController = game.gameController
        gameController.playerController.nextTurn()
        if (gameController.playerController.isRoundEnd()) {
            game.gameController.inputHandler.handleCommand(EndRoundCommand())
        }
    }

}