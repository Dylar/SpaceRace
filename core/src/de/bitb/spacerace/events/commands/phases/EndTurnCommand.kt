package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.space.control.BaseSpace

class EndTurnCommand(inputHandler: InputHandler) : PhaseCommand(inputHandler, PlayerColor.NONE) {

    override fun canExecute(space: BaseSpace): Boolean {
        return space.playerController.currentPlayer.playerData.phase.isEndTurn()
    }

    override fun execute(space: BaseSpace) {
        space.playerController.nextTurn()
    }


}