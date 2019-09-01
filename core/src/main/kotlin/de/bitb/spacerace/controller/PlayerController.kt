package de.bitb.spacerace.controller

import de.bitb.spacerace.core.PlayerColorDispender
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.utils.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerController
@Inject constructor(
        private var playerColorDispender: PlayerColorDispender
) {

    val players: MutableList<PlayerColor> = ArrayList()

    var currentPlayerData = NONE_PLAYER_DATA
    val currentColor: PlayerColor
        get() = players.firstOrNull() ?: PlayerColor.NONE

    fun changePlayer() {
        val oldPlayer = currentColor
        players.removeAt(0)
        players.add(oldPlayer)
        Logger.println("newPlayer: $currentColor")
        playerColorDispender.publishUpdate(currentColor)
    }

    fun addPlayer(playerColor: PlayerColor) {
        players.add(playerColor)
    }
}