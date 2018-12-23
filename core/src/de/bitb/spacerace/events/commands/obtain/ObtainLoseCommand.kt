package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.control.GameController

class ObtainLoseCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(space: GameController): Boolean {
        return true
    }

    override fun execute(space: GameController, inputHandler: InputHandler) {
        val lose = space.playerController.getPlayer(playerColor).substractRandomWin()
    }

}