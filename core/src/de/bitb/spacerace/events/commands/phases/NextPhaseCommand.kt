package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.Phase

class NextPhaseCommand(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return canContinue(getPlayerData(game, playerColor))
    }

    override fun execute(game: MainGame) {
        val inputHandler = game.gameController.inputHandler
        val playerData = getPlayerData(game, playerColor)
        playerData.phase = Phase.next(playerData.phase)

        Logger.println("Phase: ${playerData.phase.name}")
        when (playerData.phase) {
            Phase.MAIN1 -> inputHandler.handleCommand(StartMain1Command(playerColor))
            Phase.MOVE -> inputHandler.handleCommand(StartMoveCommand(playerColor))
            Phase.MAIN2 -> inputHandler.handleCommand(StartMain2Command(playerColor))
            Phase.END_TURN -> inputHandler.handleCommand(EndTurnCommand(playerColor))
            Phase.END_ROUND -> TODO()
        }

    }

}