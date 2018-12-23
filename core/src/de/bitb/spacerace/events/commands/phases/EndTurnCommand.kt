package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.space.control.GameController

class EndTurnCommand(inputHandler: InputHandler) : PhaseCommand(inputHandler, PlayerColor.NONE) {

    override fun canExecute(space: GameController): Boolean {
        return space.playerController.currentPlayer.playerData.phase.isEndTurn()
    }

    override fun execute(space: GameController, inputHandler: InputHandler) {
        space.playerController.nextTurn()
        if (space.playerController.isRoundEnd()) {
            inputHandler.handleCommand(EndRoundCommand(inputHandler))
        }
    }


}