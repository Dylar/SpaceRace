package de.bitb.spacerace.events.commands.obtain

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.fields.MineField

class ObtainMineCommand(playerData: PlayerData) : BaseCommand(playerData) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
        val mineField: MineField = getPlayerField(game.gameController.fieldController, playerData.playerColor) as MineField
        mineField.owner = playerData.playerColor
    }

}