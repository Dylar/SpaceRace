package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA

class ChangeWinAmountCommand(val amount: Int) : StartScreenCommand(NONE_PLAYER_DATA) {
    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        WIN_AMOUNT += amount
        WIN_AMOUNT = if (WIN_AMOUNT < 1) 1 else WIN_AMOUNT
    }

}