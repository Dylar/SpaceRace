package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.fields.MineField

class ObtainMineCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val mineField: MineField = getPlayerField(game, playerColor) as MineField
        mineField.owner = playerColor
    }

}