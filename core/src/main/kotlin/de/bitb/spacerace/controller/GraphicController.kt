package de.bitb.spacerace.controller

import de.bitb.spacerace.model.player.NONE_PLAYER
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GraphicController
@Inject constructor(
) {

    var players: MutableList<Player> = ArrayList()
    val currentPlayer: Player
        get() = players.firstOrNull() ?: NONE_PLAYER

    fun getPlayer(playerColor: PlayerColor): Player {
        return players.find { playerColor == it.playerColor } ?: NONE_PLAYER
    }

    fun clearPlayer() {
        players.clear()
    }

    fun addPlayer(player: Player) {
        players.add(player)
    }

}