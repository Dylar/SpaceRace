package de.bitb.spacerace.controller

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.player.NONE_PLAYER
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
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

    fun addPlayer(playerData: PlayerData, startField: SpaceField) {
        val color = playerData.playerColor
        val player = Player(color)

        players.add(player)
        player.setPosition(startField.gamePosition)
        player.getGameImage().color = color.color
        player.getGameImage().followImage = startField.fieldImage
    }

}