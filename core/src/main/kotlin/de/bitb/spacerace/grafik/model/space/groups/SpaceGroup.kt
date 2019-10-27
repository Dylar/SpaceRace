package de.bitb.spacerace.grafik.model.space.groups

import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.model.enums.ConnectionPoint
import de.bitb.spacerace.grafik.model.space.fields.ConnectionGraphic
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class SpaceGroup(
        val offsetX: Float = 0f,
        val offsetY: Float = 0f
) {

    private val connectionPoint: MutableMap<ConnectionPoint, MutableList<FieldGraphic>> = EnumMap(ConnectionPoint::class.java)
    val fields: MutableMap<Int, FieldGraphic> = HashMap()
    val connections: MutableList<ConnectionGraphic> = mutableListOf()

    init {
        MainGame.appComponent.inject(this)
    }

    fun getField(id: Int): FieldGraphic {
        return fields[id]!!
    }

    fun addField(addField: FieldGraphic, anchorField: FieldGraphic, horizontalMod: Float = 0f, verticalMod: Float = 0f, connection: ConnectionPoint = ConnectionPoint.NONE) {
        val posX = anchorField.getGameImage().x - offsetX + addField.getGameImage().width * horizontalMod
        val posY = anchorField.getGameImage().y - offsetY + addField.getGameImage().height * verticalMod
        addField(addField, posX, posY, connection)
    }

    open fun addField(addField: FieldGraphic,
                      posX: Float = addField.getGameImage().x,
                      posY: Float = addField.getGameImage().y,
                      connection: ConnectionPoint = ConnectionPoint.NONE) {
        addField.id = fields.size
        addField.setPosition(posX + offsetX, posY + offsetY)
        fields[addField.id] = addField
        if (connection != ConnectionPoint.NONE) {
            addConnectionPoint(connection, addField)
        }
    }

    private fun getConnectionPoint(connection: ConnectionPoint): MutableList<FieldGraphic> =
            connectionPoint[connection]
                    ?: ArrayList<FieldGraphic>().also { connectionPoint[connection] = it }

    fun addConnectionPoint(connection: ConnectionPoint, field: FieldGraphic) {
        getConnectionPoint(connection).add(field)
    }

    fun connectGroups(connection: ConnectionPoint, group: SpaceGroup) {
        val thisConnection = getConnectionPoint(connection)
        val thatConnection = group.getConnectionPoint(connection.getOpposite())
        for (index in thisConnection.withIndex()) {
            if (index.index < thatConnection.size) {
                val thisField = thisConnection[index.index]
                val thatField = thatConnection[index.index]
                this.connectFields(thisField, thatField)
            }
        }
    }

    fun connectFields(spaceField1: FieldGraphic, spaceField2: FieldGraphic) {
        val connection = ConnectionGraphic(spaceField1, spaceField2)
        connections.add(connection)
    }

}