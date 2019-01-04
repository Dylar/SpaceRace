package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.config.LANGUAGE
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.config.enums.Language
import de.bitb.spacerace.core.MainGame

class ChangeWinAmountCommand(val amount: Int, playerColor: PlayerColor = PlayerColor.NONE) : StartScreenCommand(playerColor) {
    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        WIN_AMOUNT += amount
        WIN_AMOUNT = if (WIN_AMOUNT < 3) 3 else WIN_AMOUNT
    }

}