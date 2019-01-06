package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame

class StartMain1Command(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return canContinue(getPlayerData(game, playerColor))
    }

    override fun execute(game: MainGame) {
//        gameController.phaseController.startMain1()
    }


}