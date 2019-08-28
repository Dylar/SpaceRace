package de.bitb.spacerace.controller

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.NONE_PLAYER
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.NONE_FIELD
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

    var fields: MutableMap<PositionData, SpaceField> = mutableMapOf()

    fun getPlayer(playerColor: PlayerColor) =
            players.find { playerColor == it.playerColor } ?: NONE_PLAYER

    fun getField(gamePosition: PositionData) = fields[gamePosition] ?: NONE_FIELD
    fun getPlayerField(playerColor: PlayerColor) =
            getPlayer(playerColor).gamePosition.let { playerPosition ->
                fields.keys.find { it.isPosition(playerPosition) }
                        ?.let { fields[it] }
                        ?: NONE_FIELD
            }

    fun addPlayer(playerData: PlayerData, startField: SpaceField) {
        val color = playerData.playerColor
        val player = Player(color)

        players.add(player)
        player.setPosition(startField.gamePosition)
        player.getGameImage().color = color.color
        player.getGameImage().followImage = startField.fieldImage
    }

    fun addField(spaceField: SpaceField) {
        fields[spaceField.gamePosition] = spaceField
    }

    fun clearGraphics() {
        players.clear()
        fields.clear()
    }

}