package de.bitb.spacerace.controller

import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor

class PlayerController {

    val victories: MutableMap<PlayerColor, Int> = HashMap()
    var players: MutableList<Player> = ArrayList()
    var playerMap: MutableMap<PlayerColor, Player> = HashMap()

    var currentPlayer: Player = Player.NONE
        get() = if (players.isEmpty()) Player.NONE else players[players.size - 1]

    fun isRoundEnd(): Boolean {
        for (player in players) {
            if (player.playerData.phase != Phase.END_TURN) {
                return false
            }
        }
        return true
    }

    fun nextTurn() {
        Logger.println("nextTurn1")
        val oldPlayer = players[0]
        var indexOld = oldPlayer.getGameImage().zIndex + 1 //TODO do it in gui
        for (ship in players) {
            ship.getGameImage().zIndex = indexOld++
        }
        players.add(oldPlayer)
        players.removeAt(0)

        oldPlayer.playerData.playerItems.removeUsedItems()
    }

    fun getPlayer(playerColor: PlayerColor): Player {
        for (player in players) {
            if (playerColor == player.playerData.playerColor) {
                return player
            }
        }
        return Player.NONE
    }

    fun clearPlayer() {
        PlayerColor.values().forEach { field -> victories[field] = 0 }
        players.clear()
        playerMap.clear()
    }

    fun getWinner(): PlayerColor {
        var winner: PlayerColor = PlayerColor.NONE
        victories.forEach { entrySet ->
            if (entrySet.value == WIN_AMOUNT) {
                winner = entrySet.key
                return@forEach
            }
        }
        return winner
    }
}