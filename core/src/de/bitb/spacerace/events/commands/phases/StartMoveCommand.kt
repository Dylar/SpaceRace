package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame

class StartMoveCommand(playerColor: PlayerColor) : PhaseCommand(playerColor) {

    override fun canExecute(game: MainGame): Boolean {
        return canContinue(getPlayerData(game, playerColor))
    }

    override fun execute(game: MainGame) {
        val playerData = getPlayerData(game, playerColor)
        playerData.steps.add(playerData.fieldPosition)
    }

}