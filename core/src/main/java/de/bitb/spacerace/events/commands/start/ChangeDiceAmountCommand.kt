package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.config.DICE_MAX
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA

class ChangeDiceAmountCommand(val amount: Int) : StartScreenCommand(NONE_PLAYER_DATA) {
    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        DICE_MAX += amount
        DICE_MAX = if (DICE_MAX < 1) 1 else DICE_MAX
    }

}