package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.config.DICE_MAX
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.config.LANGUAGE
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.config.enums.Language
import de.bitb.spacerace.core.MainGame

class ChangeDiceAmountCommand(val amount: Int, playerColor: PlayerColor = PlayerColor.NONE) : StartScreenCommand(playerColor) {
    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        DICE_MAX += amount
        DICE_MAX = if (DICE_MAX < 1) 1 else DICE_MAX
    }

}