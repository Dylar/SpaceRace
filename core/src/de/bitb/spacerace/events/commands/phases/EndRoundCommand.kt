package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.PlayerColor

class EndRoundCommand() : PhaseCommand(PlayerColor.NONE) {

    override fun canExecute(game: MainGame): Boolean {
        return game.gameController.playerController.isRoundEnd()
    }

    override fun execute(game: MainGame) {
        game.gameController.fieldController.harvestOres()
        val players = game.gameController.playerController.players

        for (player in players) {
            val playerData = player.playerData
//            val saveData = playerData.copy() //TODO save me for history
            playerData.nextRound()
        }
    }

}