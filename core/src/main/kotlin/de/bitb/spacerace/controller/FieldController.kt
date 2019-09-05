package de.bitb.spacerace.controller

import de.bitb.spacerace.config.DEBUG_WIN_FIELD
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemImage
import de.bitb.spacerace.model.items.disposable.moving.MovingItem
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.NONE_FIELD
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.model.space.fields.SpaceField
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class FieldController
@Inject constructor(
        val mapDataSource: MapDataSource,
        val graphicController: GraphicController
) {

    var currentGoal: PositionData? = null
    var fieldsMap: MutableMap<FieldType, MutableList<SpaceField>> = EnumMap(FieldType::class.java)

    init {
        MainGame.appComponent.inject(this)
        FieldType.values().forEach { field -> fieldsMap[field] = ArrayList() }
    }

    fun addField(spaceField: SpaceField) {
        fieldsMap[spaceField.fieldType]!!.add(spaceField)
    }

    fun moveMovables() {

        fun getField(item: Item): SpaceField {
            return graphicController.fieldGraphics.values
                    .filter { it.disposedItems.contains(item) }
                    .firstOrNull() ?: NONE_FIELD
        }

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
        graphicController.fieldGraphics.values
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

    fun setRandomGoal(): Pair<PositionData?, PositionData> {
        val oldGoal = currentGoal
        val goals = fieldsMap[FieldType.GOAL]!!
        currentGoal =
                if (DEBUG_WIN_FIELD) goals.first().gamePosition
                else goals[(Math.random() * goals.size).toInt()].gamePosition

        return oldGoal to currentGoal!!
    }

    fun setConnectionColor(connectionInfo: ConnectionInfo) {
        graphicController.connectionGraphics.forEach { connection ->
            connection.setColor(connectionCanBeCrossed(connection, connectionInfo))
        }
    }

    fun connectionCanBeCrossed(spaceConnection: SpaceConnection, connectionInfo: ConnectionInfo) =
            with(connectionInfo) {
                (phase == Phase.MOVE
                        && spaceConnection.isConnected(position)
                        && (stepsLeft || spaceConnection.isConnection(position, previousPosition)))
            }
}