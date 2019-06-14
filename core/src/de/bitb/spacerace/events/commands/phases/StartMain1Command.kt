package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData

class StartMain1Command(playerData: PlayerData) : PhaseCommand(playerData) {

    override fun canExecute(game: MainGame): Boolean {
        return true
    }

    override fun execute(game: MainGame) {
    }


}