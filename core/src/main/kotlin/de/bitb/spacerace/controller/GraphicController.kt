package de.bitb.spacerace.controller

import de.bitb.spacerace.config.COLOR_CONNECTED
import de.bitb.spacerace.config.COLOR_DISCONNECTED
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.NONE_PLAYER
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerItems
import de.bitb.spacerace.model.space.fields.NONE_FIELD
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.ConnectionList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GraphicController
@Inject constructor(
        val playerController: PlayerController
) {

    var players: MutableList<Player> = ArrayList()
    val currentPlayer: Player
        get() = players.firstOrNull() ?: NONE_PLAYER

    var fields: MutableMap<PositionData, SpaceField> = mutableMapOf()
    var connections: ConnectionList = ConnectionList(this, playerController)

    fun getPlayer(playerColor: PlayerColor) =
            players.find { playerColor == it.playerColor } ?: NONE_PLAYER

    fun getField(gamePosition: PositionData) =
            fields.keys.find { it.isPosition(gamePosition) }
                    ?.let { fields[it] }
                    ?: NONE_FIELD

    fun getPlayerField(playerColor: PlayerColor) =
            getField(getPlayer(playerColor).gamePosition)

    fun getPlayerItems(playerColor: PlayerColor): PlayerItems =
            getPlayer(playerColor).playerItems

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

    fun addConnection(spaceField1: SpaceField, spaceField2: SpaceField) {
        val connection = SpaceConnection(spaceField1, spaceField2)
        connections.add(connection)
        spaceField1.connections.add(connection)
        spaceField2.connections.add(connection)
    }

    fun movePlayer(moveInfo: MoveInfo) {
        val player = getPlayer(moveInfo.playerColor)
        val playerImage = player.playerImage
        val targetField = getField(moveInfo.position)
        val fieldImage = targetField.fieldImage

        playerImage.moveToPoint(playerImage,
                fieldImage,
                playerImage.getNONEAction(playerImage, fieldImage))
        player.gamePosition.setPosition(moveInfo.position) //TODO maybe we dont need this

    }

}

data class NextPhaseInfo(
        var playerData: PlayerData,
        var phase: Phase
)

data class ConnectionInfo(
        var position: PositionData,
        var stepsLeft: Boolean,
        var previousPosition: PositionData,
        var phase: Phase
)

data class MoveInfo(
        var playerColor: PlayerColor,
        var position: PositionData,
        var stepsLeft: Boolean,
        var previousPosition: PositionData,
        var phase: Phase = Phase.MOVE
)

fun PlayerData.toConnectionInfo(position: PositionData) = ConnectionInfo(position, areStepsLeft(), previousStep, phase)

fun MoveInfo.toConnectionInfo(): ConnectionInfo = ConnectionInfo(position, stepsLeft, previousPosition, phase)
fun NextPhaseInfo.toConnectionInfo(position: PositionData): ConnectionInfo = ConnectionInfo(position, playerData.areStepsLeft(), playerData.previousStep, phase)