package de.bitb.spacerace.controller

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
import de.bitb.spacerace.utils.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GraphicController
@Inject constructor(
        val playerController: PlayerController
) {

    var playerGraphics: MutableList<Player> = ArrayList()
    val currentPlayerGraphic: Player
        get() = playerGraphics.firstOrNull() ?: NONE_PLAYER

    var fieldGraphics: MutableMap<PositionData, SpaceField> = mutableMapOf()
    var connectionGraphics: ConnectionList = ConnectionList(this, playerController)

    fun getPlayer(playerColor: PlayerColor) =
            playerGraphics.find { playerColor == it.playerColor } ?: NONE_PLAYER

    fun getField(gamePosition: PositionData) =
            fieldGraphics.keys.find { it.isPosition(gamePosition) }
                    ?.let { fieldGraphics[it] }
                    ?: NONE_FIELD

    fun getPlayerField(playerColor: PlayerColor) =
            getField(getPlayer(playerColor).gamePosition)

    fun getPlayerItems(playerColor: PlayerColor): PlayerItems =
            getPlayer(playerColor).playerItems

    fun addPlayer(playerData: PlayerData, startField: SpaceField) {
        val color = playerData.playerColor
        val player = Player(color)

        playerGraphics.add(player)
        player.setPosition(startField.gamePosition)
        player.getGameImage().color = color.color
        player.getGameImage().followImage = startField.fieldImage
    }

    fun addField(spaceField: SpaceField) {
        fieldGraphics[spaceField.gamePosition] = spaceField
    }

    fun clearGraphics() {
        playerGraphics.clear()
        fieldGraphics.clear()
        connectionGraphics.clear()
    }

    fun addConnection(spaceField1: SpaceField, spaceField2: SpaceField) {
        val connection = SpaceConnection(spaceField1, spaceField2)
        connectionGraphics.add(connection)
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

    fun changePlayer() {
        val oldPlayer = playerGraphics[0]
        var indexOld = oldPlayer.getGameImage().zIndex + 1
        playerGraphics.removeAt(0)
        playerGraphics.add(oldPlayer)

        playerGraphics.forEach { player ->
            player.getGameImage().zIndex = indexOld++
        }

        Logger.println("oldPlayer: ${oldPlayer.playerColor}")
        //TODO items in db
        oldPlayer.playerItems.removeUsedItems()
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