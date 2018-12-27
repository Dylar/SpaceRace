package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame

class StartMain1Command() : PhaseCommand(PlayerColor.NONE) {

    override fun canExecute(game: MainGame): Boolean {
        val gameController = game.gameController
        return gameController.phaseController.canContinue(gameController.playerController.currentPlayer.playerData)
    }

    override fun execute(game: MainGame) {
//        gameController.phaseController.startMain1()
    }


}