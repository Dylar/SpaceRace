package de.bitb.spacerace.controller

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.player.MoveCommand
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemImage
import de.bitb.spacerace.model.items.disposable.moving.MovingItem
import de.bitb.spacerace.model.objecthandling.NONE_POSITION
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.NONE_PLAYER
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerItems
import de.bitb.spacerace.model.space.fields.NONE_SPACE_FIELD
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.ConnectionList
import de.bitb.spacerace.usecase.game.action.ConnectionResult
import de.bitb.spacerace.usecase.game.action.MoveResult
import de.bitb.spacerace.usecase.game.action.NextPhaseResult
import de.bitb.spacerace.utils.Logger
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GraphicController
@Inject constructor(
        val playerController: PlayerController
) {

    var playerGraphics: MutableList<Player> = ArrayList()
    val currentPlayerGraphic: Player
        get() = playerGraphics.find { playerController.currentColor == it.playerColor }
                ?: NONE_PLAYER

    var fieldGraphics: MutableMap<PositionData, SpaceField> = mutableMapOf()
    var connectionGraphics: ConnectionList = ConnectionList()

    fun getPlayerGraphic(playerColor: PlayerColor) =
            playerGraphics.find { playerColor == it.playerColor } ?: NONE_PLAYER

    fun getFieldGraphic(gamePosition: PositionData) =
            fieldGraphics.keys.find { it.isPosition(gamePosition) }
                    ?.let { fieldGraphics[it] }
                    ?: NONE_SPACE_FIELD

    fun getPlayerFieldGraphic(playerColor: PlayerColor) =
            getFieldGraphic(getPlayerGraphic(playerColor).gamePosition)

    @Deprecated("")
    fun getPlayerItems(playerColor: PlayerColor): PlayerItems =
            getPlayerGraphic(playerColor).playerItems

    fun addPlayer(playerColor: PlayerColor, startField: SpaceField) {
        val player = Player(playerColor)

        player.setPosition(startField.gamePosition)
        player.getGameImage().apply {
            color = playerColor.color
            followImage = startField.fieldImage
        }

        playerGraphics.add(player)
    }

    fun addField(spaceField: SpaceField) {
        fieldGraphics[spaceField.gamePosition] = spaceField
        spaceField.getGameImage().addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(MoveCommand(spaceField.gamePosition, playerController.currentPlayerData))
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
        val player = getPlayerGraphic(moveResult.playerColor)
        val playerImage = player.playerImage
        val targetField = getFieldGraphic(moveResult.position)
        val fieldImage = targetField.fieldImage

        playerImage.moveToPoint(playerImage,
                fieldImage,
                playerImage.getNONEAction(playerImage, fieldImage))
        player.gamePosition.setPosition(moveResult.position)
    }

    fun teleportPlayer(playerColor: PlayerColor, position: PositionData) {
        val player = getPlayerGraphic(playerColor)
        val playerImage = player.playerImage
        playerImage.setFieldPosition(player, position)
    }

    fun changePlayer() {
        val oldPlayer = playerGraphics[0]
        var indexOld = oldPlayer.getGameImage().zIndex
        playerGraphics.removeAt(0)
        playerGraphics.add(oldPlayer)

        playerGraphics
                .map { it.getGameImage() }
                .reversed()
                .forEach { it.zIndex = indexOld++ }

        Logger.println("oldPlayer: ${oldPlayer.playerColor}")
        //TODO items in db
        oldPlayer.playerItems.removeUsedItems()
    }

    fun setGoal(oldGoal: PositionData = NONE_POSITION, currentGoal: PositionData = NONE_POSITION) {
        val oldGoalGraphic = getFieldGraphic(oldGoal)
        val currentGoalGraphic = getFieldGraphic(currentGoal)
        oldGoalGraphic.fieldImage.setBlinkColor(null)
        currentGoalGraphic.fieldImage.setBlinkColor(currentGoalGraphic.fieldType.color)
    }


    fun moveMovables() {

        fun getField(item: Item): SpaceField {
            return fieldGraphics.values
                    .filter { it.disposedItems.contains(item) }
                    .firstOrNull() ?: NONE_SPACE_FIELD
        }

        val moveItem = { item: MovingItem, toRemove: MutableList<Item> ->
            val field = getField(item)
            val list = field.connections
            val con = list[(Math.random() * list.size).toInt()]
            val newField = con.getOpposite(field.gamePosition)
            newField.disposedItems.add(item)

            val itemImage = item.getGameImage() as ItemImage
            val point = itemImage.getRotationPosition(itemImage, newField.getGameImage())

            item.gamePosition.setPosition(newField.gamePosition)
            itemImage.moveTo(item.getGameImage(), point, doAfter = *arrayOf(itemImage.getRotationAction(itemImage, newField.getGameImage())))
            toRemove.add(item)
        }

        val fieldList: MutableList<SpaceField> = ArrayList()
        fieldGraphics.values
                .filter { it.disposedItems.isNotEmpty() }
                .forEach { fieldList.add(it) }

        fieldList.forEach { field: SpaceField ->
            val toRemove: MutableList<Item> = ArrayList()
            field.disposedItems.forEach {
                if (it is MovingItem && it.getGameImage().isIdling()) {
                    moveItem(it, toRemove)
                }
            }
            field.disposedItems.removeAll(toRemove)
        }

    }
}

fun PlayerData.toConnectionResult(position: PositionData) = ConnectionResult(position, areStepsLeft(), previousStep, phase)

fun MoveResult.toConnectionResult(): ConnectionResult = ConnectionResult(position, stepsLeft, previousPosition, phase)
fun NextPhaseResult.toConnectionResult(position: PositionData): ConnectionResult = ConnectionResult(position, player.areStepsLeft(), player.previousStep, player.phase)