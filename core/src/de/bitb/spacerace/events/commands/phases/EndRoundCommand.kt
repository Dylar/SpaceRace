package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame

class EndRoundCommand() : PhaseCommand(PlayerColor.NONE) {

    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.playerController.isRoundEnd()
    }

    override fun execute(game: MainGame) {
        game.gameController.fieldController.harvestOres()
        game.gameController.playerController.nextRound()
    }

}