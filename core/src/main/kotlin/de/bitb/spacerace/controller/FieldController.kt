package de.bitb.spacerace.controller

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemImage
import de.bitb.spacerace.model.items.disposable.moving.MovingItem
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.ConnectionList
import de.bitb.spacerace.model.space.maps.MapCollection
import de.bitb.spacerace.model.space.maps.SpaceMap
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class FieldController
@Inject constructor(
//        val playerController: PlayerController
) : DefaultFunction {

    lateinit var map: SpaceMap
    var spaceMap: MapCollection = MapCollection.RANDOM

    var currentGoal: SpaceField = SpaceField.NONE

    var fields: MutableList<SpaceField> = mutableListOf()
    var fieldsMap: MutableMap<FieldType, MutableList<SpaceField>> = EnumMap(FieldType::class.java)
    lateinit var connections: ConnectionList

    init {
        MainGame.appComponent.inject(this)
//        clearField()
    }

    fun clearField() {
//        fields.forEach { it.fieldImage.remove() }
        fields.clear()
        FieldType.values().forEach { field -> fieldsMap[field] = ArrayList() }
    }

    fun getField(positionData: PositionData): SpaceField {
        return fields.find { it.gamePosition.isPosition(positionData) } ?: SpaceField.NONE
    }

    fun getField(item: Item): SpaceField {
        return fields.find { it.disposedItems.contains(item) } ?: SpaceField.NONE
    }

    fun addFieldMap(spaceField: SpaceField) {
        fieldsMap[spaceField.fieldType]!!.add(spaceField)
    }

    fun addShip(player: Player, spaceField: SpaceField) {
        player.setPosition(spaceField.gamePosition)
        player.getGameImage().color = player.playerColor.color
        player.getGameImage().followImage = spaceField.fieldImage
    }

    fun addConnection(spaceField1: SpaceField, spaceField2: SpaceField) {
        initConnection()
        val connection = SpaceConnection(spaceField1, spaceField2)
        connections.add(connection)
        spaceField1.connections.add(connection)
        spaceField2.connections.add(connection)
    }

    private fun initConnection() {
        if (!::connections.isInitialized) {
            connections = ConnectionList()
        }
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
        fields.filter { it.disposedItems.isNotEmpty() }
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

    fun setRandomGoal() {
        currentGoal.fieldImage.setBlinkColor(null)
        currentGoal = map.getRandomGoal()
        currentGoal.fieldImage.setBlinkColor(Color(currentGoal.fieldType.color))
    }

}