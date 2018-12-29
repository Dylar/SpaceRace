package de.bitb.spacerace.base

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.PlayerData

interface DefaultFunction {

    fun getPlayerData(game: MainGame): PlayerData {
        return game.gameController.playerController.currentPlayer.playerData
    }
}