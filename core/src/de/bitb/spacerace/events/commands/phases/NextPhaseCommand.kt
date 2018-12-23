package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.space.control.GameController

class NextPhaseCommand(inputHandler: InputHandler, playerColor: PlayerColor) : PhaseCommand(inputHandler, playerColor) {
    override fun canExecute(space: GameController): Boolean {
        return space.phaseController.canContinue(space.playerController.currentPlayer.playerData)
    }

    override fun execute(space: GameController, inputHandler: InputHandler) {
        val playerData = space.playerController.currentPlayer.playerData
        playerData.phase = Phase.next(playerData.phase)

        Logger.println("Phase: ${playerData.phase.name}")
        when (playerData.phase) {
            Phase.MAIN1 -> inputHandler.handleCommand(StartMain1Command(inputHandler))
            Phase.MOVE -> inputHandler.handleCommand(StartMoveCommand(inputHandler))
            Phase.MAIN2 -> inputHandler.handleCommand(StartMain2Command(inputHandler, playerColor))
            Phase.END_TURN -> inputHandler.handleCommand(EndTurnCommand(inputHandler))
            Phase.END_ROUND -> TODO()
        }

    }

}