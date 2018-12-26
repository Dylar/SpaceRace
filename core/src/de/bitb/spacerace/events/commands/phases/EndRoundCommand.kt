package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.space.control.GameController

class EndRoundCommand() : PhaseCommand(PlayerColor.NONE) {

    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.playerController.isRoundEnd()
    }

    override fun execute(game: MainGame) {
        game.gameController.playerController.nextRound()
    }

}