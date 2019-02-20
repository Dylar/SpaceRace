package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import org.greenrobot.eventbus.EventBus

class EndTurnCommand(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val playerController = game.gameController.playerController
        playerController.nextTurn()

        if (playerController.isRoundEnd()) {
            EventBus.getDefault().post(EndRoundCommand())
        }
    }

}