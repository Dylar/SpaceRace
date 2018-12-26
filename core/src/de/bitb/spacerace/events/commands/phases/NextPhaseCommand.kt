package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.Phase

class NextPhaseCommand(playerColor: PlayerColor) : PhaseCommand(playerColor) {
    override fun canExecute(game: MainGame): Boolean {
        val gameController = game.gameController
        return gameController.phaseController.canContinue(gameController.playerController.currentPlayer.playerData)
    }

    override fun execute(game: MainGame) {
        val gameController = game.gameController
        val inputHandler = gameController.inputHandler
        val playerData = gameController.playerController.currentPlayer.playerData
        playerData.phase = Phase.next(playerData.phase)

        Logger.println("Phase: ${playerData.phase.name}")
        when (playerData.phase) {
            Phase.MAIN1 -> inputHandler.handleCommand(StartMain1Command())
            Phase.MOVE -> inputHandler.handleCommand(StartMoveCommand())
            Phase.MAIN2 -> inputHandler.handleCommand(StartMain2Command(playerColor))
            Phase.END_TURN -> inputHandler.handleCommand(EndTurnCommand(playerColor))
            Phase.END_ROUND -> TODO()
        }

    }

}