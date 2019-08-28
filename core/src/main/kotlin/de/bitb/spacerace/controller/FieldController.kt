package de.bitb.spacerace.controller

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemImage
import de.bitb.spacerace.model.items.disposable.moving.MovingItem
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.NONE_FIELD
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
        val graphicController: GraphicController,
        val playerController: PlayerController
) {

    lateinit var map: SpaceMap
    var spaceMap: MapCollection = MapCollection.RANDOM

    var currentGoal: SpaceField? = null
    var fieldsMap: MutableMap<FieldType, MutableList<SpaceField>> = EnumMap(FieldType::class.java)

    var connections: ConnectionList = ConnectionList(graphicController, playerController)

    init {
        MainGame.appComponent.inject(this)
//        clearField()
        FieldType.values().forEach { field -> fieldsMap[field] = ArrayList() }
    }

//    fun clearField() {
////        fields.forEach { it.fieldImage.remove() }
//        fields.clear()
//        FieldType.values().forEach { field -> fieldsMap[field] = ArrayList() }
//    }

    fun getField(groupId: Int, fieldId: Int) = map.groups[groupId].getField(fieldId)

    fun getField(item: Item): SpaceField {
        return graphicController.fields.values
                .filter { it.disposedItems.contains(item) }
                .firstOrNull() ?: NONE_FIELD
    }

    fun addFieldMap(spaceField: SpaceField) {
        fieldsMap[spaceField.fieldType]!!.add(spaceField)
    }

    fun addConnection(spaceField1: SpaceField, spaceField2: SpaceField) {
        val connection = SpaceConnection(spaceField1, spaceField2)
        connections.add(connection)
        spaceField1.connections.add(connection)
        spaceField2.connections.add(connection)
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
        graphicController.fields.values
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

    fun setRandomGoal() {
        currentGoal?.fieldImage?.setBlinkColor(null)
        currentGoal = map.getRandomGoal()
        currentGoal?.fieldImage?.setBlinkColor(Color(currentGoal?.fieldType?.color))

    }

}