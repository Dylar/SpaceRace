package de.bitb.spacerace.core.controller

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.config.DEFAULT_SHIP
import de.bitb.spacerace.core.events.commands.player.MoveCommand
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.model.items.ItemGraphic
import de.bitb.spacerace.grafik.model.items.ItemImage
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.items.createGraphic
import de.bitb.spacerace.grafik.model.objecthandling.NONE_POSITION
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.objecthandling.getRunnableAction
import de.bitb.spacerace.grafik.model.player.NONE_PLAYER
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.grafik.model.player.PlayerGraphics
import de.bitb.spacerace.grafik.model.player.PlayerImage
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic
import de.bitb.spacerace.grafik.model.space.fields.NONE_SPACE_FIELD
import de.bitb.spacerace.grafik.model.space.groups.ConnectionList
import de.bitb.spacerace.usecase.game.action.MoveResult
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GraphicController
@Inject constructor(
        val playerController: PlayerController
) {

    var playerGraphics: MutableList<PlayerGraphics> = ArrayList()
    val currentPlayerGraphic: PlayerGraphics
        get() = playerGraphics.find { playerController.currentColor == it.playerColor }
                ?: NONE_PLAYER

    var fieldGraphics: MutableMap<PositionData, FieldGraphic> = mutableMapOf()
    var connectionGraphics: ConnectionList = ConnectionList()

    fun getPlayerGraphic(playerColor: PlayerColor) =
            playerGraphics.find { playerColor == it.playerColor } ?: NONE_PLAYER

    fun getFieldGraphic(gamePosition: PositionData) =
            fieldGraphics.keys.find { it.isPosition(gamePosition) }
                    ?.let { fieldGraphics[it] }
                    ?: NONE_SPACE_FIELD

    fun addPlayer(playerColor: PlayerColor, startField: FieldGraphic) {
        val playerImage = PlayerImage(playerColor, DEFAULT_SHIP)
        val player = PlayerGraphics(playerColor, playerImage)

        player.setPosition(startField.gamePosition)
        player.getGameImage().apply {
            color = playerColor.color
            followImage = startField.fieldImage
        }

        playerGraphics.add(player)
    }

    fun addField(spaceField: FieldGraphic) {
        fieldGraphics[spaceField.gamePosition] = spaceField
        spaceField.getGameImage().addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(MoveCommand.get(spaceField.gamePosition, playerController.currentColor))
                return true
            }
        })
    }

    fun clearGraphics() {
        playerGraphics.clear()
        fieldGraphics.clear()
        connectionGraphics.clear()
    }

    fun movePlayer(moveResult: MoveResult) {
        val player = getPlayerGraphic(moveResult.player.playerColor)
        val playerImage = player.playerImage
        val targetField = getFieldGraphic(moveResult.position)
        val fieldImage = targetField.fieldImage

        playerImage.moveToPoint(playerImage,
                fieldImage,
                playerImage.getNONEAction(playerImage, fieldImage),
                getRunnableAction(Runnable {
                    player.gamePosition.setPosition(moveResult.position)
                }))
    }

    //TODO not working !
    fun teleportPlayer(playerColor: PlayerColor, position: PositionData) {
        val player = getPlayerGraphic(playerColor)
        val playerImage = player.playerImage
        playerImage.setFieldPosition(player, position)
    }

    fun changePlayer() {
        var graphicPlainIndex = playerGraphics
                .map { it.getGameImage().zIndex }
                .max()!!

        val playerIndex = playerController.currentPlayerIndex
        for (i in playerIndex until playerGraphics.size) {
            playerGraphics[i].getGameImage().zIndex = graphicPlainIndex--
        }

        for (i in 0 until playerIndex) {
            playerGraphics[i].getGameImage().zIndex = graphicPlainIndex--
        }
    }

    fun setGoal(oldGoal: PositionData = NONE_POSITION, currentGoal: PositionData = NONE_POSITION) {
        val oldGoalGraphic = getFieldGraphic(oldGoal)
        val currentGoalGraphic = getFieldGraphic(currentGoal)
        oldGoalGraphic.fieldImage.setBlinkColor(null)
        currentGoalGraphic.fieldImage.setBlinkColor(currentGoalGraphic.fieldType.color)
    }

    fun setConnectionColor(player: PlayerData, fields: MutableList<FieldData>) {
        connectionGraphics.forEach { connection ->
            connection.also { (field1, field2) ->
                val playerPosition = player.gamePosition
                val field1Position = field1.gamePosition
                val field2Position = field2.gamePosition
                val isConnected = playerPosition.isPosition(field1Position)
                        && fields.any { field2Position.isPosition(it.gamePosition) }
                        || playerPosition.isPosition(field2Position)
                        && fields.any { field1Position.isPosition(it.gamePosition) }
                connection.setColor(isConnected)
            }
        }
    }

    fun setMineOwner(player: PlayerData) {
        getFieldGraphic(player.gamePosition).setBlinkColor(player.playerColor.color)
    }

    fun getStorageItemMap(playerData: PlayerData): Map<ItemType, ItemGraphic> =
            playerData.storageItems
                    .map { it.itemInfo.createGraphic(playerData.playerColor) }
                    .associateBy { it.itemType }


    fun moveItems(fromFieldData: FieldData, toFieldData: FieldData, itemData: ItemData) { //TODO make items moveable again !
        val fromField = getFieldGraphic(fromFieldData.gamePosition)
        val toField = getFieldGraphic(toFieldData.gamePosition)
        val item = fromField.disposedItems.first { it.itemType == itemData.itemInfo.type }
        val itemImage = item.getGameImage() as ItemImage
        val point = itemImage.getRotationPosition(itemImage, toField.getGameImage())

        item.gamePosition.setPosition(toField.gamePosition)
        itemImage.moveTo(item.getGameImage(), point, doAfter = *arrayOf(itemImage.getRotationAction(itemImage, toField.getGameImage())))

        fromField.disposedItems.remove(item)
        toField.disposedItems.add(item)
    }
}