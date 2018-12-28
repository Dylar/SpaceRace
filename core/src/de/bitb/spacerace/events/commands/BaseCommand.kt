package de.bitb.spacerace.events.commands

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.BaseEvent
import de.bitb.spacerace.model.player.PlayerData

abstract class BaseCommand(var playerColor: PlayerColor = PlayerColor.NONE) : BaseEvent() {

    fun getPlayerData(game: MainGame): PlayerData {
        return game.gameController.playerController.getPlayer(playerColor).playerData
    }

    abstract fun canExecute(game: MainGame): Boolean
    abstract fun execute(game: MainGame)
}