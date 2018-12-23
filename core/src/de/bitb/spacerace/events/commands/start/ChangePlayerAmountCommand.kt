package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.config.PLAYER_AMOUNT
import de.bitb.spacerace.config.PLAYER_AMOUNT_MAX
import de.bitb.spacerace.config.PLAYER_AMOUNT_MIN
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.space.control.GameController

class ChangePlayerAmountCommand(playerColor: PlayerColor = PlayerColor.NONE) : StartCommand(playerColor) {
    override fun canExecute(space: GameController): Boolean {
        return true
    }

    override fun execute(space: GameController, inputHandler: InputHandler) {
        PLAYER_AMOUNT = if (PLAYER_AMOUNT == PLAYER_AMOUNT_MAX) PLAYER_AMOUNT_MIN else ++PLAYER_AMOUNT
    }

}