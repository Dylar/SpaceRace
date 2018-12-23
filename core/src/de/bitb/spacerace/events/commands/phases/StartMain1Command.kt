package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.space.control.GameController

class StartMain1Command(inputHandler: InputHandler) : PhaseCommand(inputHandler, PlayerColor.NONE) {

    override fun canExecute(space: GameController): Boolean {
        return space.phaseController.canContinue(space.playerController.currentPlayer.playerData)
    }

    override fun execute(space: GameController, inputHandler: InputHandler) {
//        gameController.phaseController.startMain1()
    }


}