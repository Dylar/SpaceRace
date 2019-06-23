package de.bitb.spacerace.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.player.MoveCommand
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemImage
import de.bitb.spacerace.model.items.disposable.moving.MovingItem
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.ConnectionList
import de.bitb.spacerace.model.space.groups.SpaceGroup
import de.bitb.spacerace.model.space.maps.MapCollection
import de.bitb.spacerace.model.space.maps.SpaceMap
import org.greenrobot.eventbus.EventBus

class FieldController(var playerController: PlayerController) : DefaultFunction {

    lateinit var map: SpaceMap
    var spaceMap: MapCollection = MapCollection.RANDOM

    var currentGoal: SpaceField = SpaceField.NONE

    var fields: MutableList<SpaceField> = mutableListOf()
    var fieldsMap: MutableMap<FieldType, MutableList<SpaceField>> = HashMap()
    var connections: ConnectionList = ConnectionList(playerController)

    init {
        clearField()
    }

    private fun clearField() {
//        fields.forEach { it.fieldImage.remove() }
        fields.clear()
        connections.clear()
        FieldType.values().forEach { field -> fieldsMap[field] = ArrayList() }
    }

    fun getField(positionData: PositionData): SpaceField {
        return fields.find { it.gamePosition.isPosition(positionData) } ?: SpaceField.NONE
    }

    fun getField(item: Item): SpaceField {
        return fields.find { it.disposedItems.contains(item) } ?: SpaceField.NONE
    }

    fun addShip(player: Player, spaceField: SpaceField) {
        player.setPosition(spaceField.gamePosition)
        player.getGameImage().color = player.playerColor.color
        player.getGameImage().followImage = spaceField.fieldImage
    }

    fun addField(gameController: GameController, spaceField: SpaceField) {
        spaceField.getGameImage().addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(MoveCommand(spaceField, playerController.currentPlayerData))
                return true
            }
        })
        fields.add(spaceField)
        addFieldMap(spaceField)
    }

    private fun addFieldMap(spaceField: SpaceField) {
        fieldsMap[spaceField.fieldType]!!.add(spaceField)
    }

    fun initMap(gameController: GameController): SpaceMap {
        clearField()
        return spaceMap
                .createMap(gameController)
                .also {
                    map = it
                    setRandomGoal()
                    addFields(gameController, *it.groups.toTypedArray())
                }
    }

    fun addFields(gameController: GameController, vararg spaceGroups: SpaceGroup) {
        for (spaceGroup in spaceGroups) {
            for (field in spaceGroup.fields.entries.withIndex()) {
                addField(gameController, field.value.value)
            }
        }
    }

    fun addConnection(spaceField1: SpaceField, spaceField2: SpaceField) {
        val connection = SpaceConnection(spaceField1, spaceField2)
        connections.add(connection)
        spaceField1.connections.add(connection)
        spaceField2.connections.add(connection)
    }

    fun getRandomTunnel(playerColor: PlayerColor): SpaceField {
        val playerPosition = getPlayerPosition(playerController, playerColor)
        val tunnelList = fieldsMap[FieldType.TUNNEL]!!
        var tunnel = tunnelList[(Math.random() * tunnelList.size).toInt()]

        while (tunnel.gamePosition.isPosition(playerPosition)) {
            tunnel = tunnelList[(Math.random() * tunnelList.size).toInt()]
        }
        return tunnel
    }

    fun moveMovables() {
        val moveItem = { item: MovingItem, toRemove: MutableList<Item> ->
            val field = getField(item)
            val list = field.connections
            val con = list[(Math.random() * list.size).toInt()]
            val newField = con.getOpposite(field)
            newField.disposedItems.add(item)

            val itemImage = item.getGameImage() as ItemImage
            val point = itemImage.getRotationPosition(itemImage, newField.getGameImage())

            item.gamePosition.setPosition(newField.gamePosition)
            itemImage.moveTo(item.getGameImage(), point, doAfter = *arrayOf(itemImage.getRotationAction(itemImage, newField.getGameImage())))
            toRemove.add(item)
        }

        val fieldList: MutableList<SpaceField> = ArrayList()
        fields.forEach {
            if (it.disposedItems.isNotEmpty()) {
                fieldList.add(it)
            }
        }
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

    fun setRandomGoal() {
        currentGoal.fieldImage.setBlinkColor(null)
        currentGoal = map.getRandomGoal()
        currentGoal.fieldImage.setBlinkColor(Color(currentGoal.fieldType.color))
    }

}