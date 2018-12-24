package de.bitb.spacerace.model.space.groups

import com.badlogic.gdx.scenes.scene2d.Group
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.control.FieldController
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.model.space.fields.SpaceField

open class SpaceGroup(val space: GameController, val offsetX: Float = 0f, val offsetY: Float = 0f) : Group() {

    private val connectionPoint: MutableMap<ConnectionPoint, MutableList<SpaceField>> = HashMap()
    val fields: MutableMap<Int, SpaceField> = HashMap()

    fun getField(id: Int): SpaceField {
        return fields[id]!!
    }

    fun addField(addField: SpaceField, anchorField: SpaceField, horizontalMod: Float = 1f, verticalMod: Float = 1f, connection: ConnectionPoint = ConnectionPoint.NONE) {
        val posX = anchorField.x + addField.x * horizontalMod
        val posY = anchorField.y + addField.y * verticalMod
        addField(addField, posX, posY, connection)
    }

    fun addField(addField: SpaceField, posX: Float = addField.x, posY: Float = addField.y, connection: ConnectionPoint = ConnectionPoint.NONE) {
//        val posX = anchorField.x + addField.x * horizontalMod
//        val posY = anchorField.y + addField.y * verticalMod

        addField.id = fields.size
        addField.group = this
        addField.setPosition(posX, posY)
        fields[addField.id] = addField
        if (connection != ConnectionPoint.NONE) {
            getConnection(connection).add(addField)
        }
        addActor(addField)
    }

    fun getConnection(connection: ConnectionPoint): MutableList<SpaceField> {
        var list = connectionPoint[connection]
        if (list == null) {
            list = ArrayList()
            connectionPoint[connection] = list
        }
        return list
    }

    fun addConnectionPoint(connection: ConnectionPoint, field: SpaceField) {
        getConnection(connection).add(field)
    }

    fun connect(connection: ConnectionPoint, group: SpaceGroup) {
        val thisConnection = getConnection(connection)
        val thatConnection = group.getConnection(connection.getOpposite())
        for (index in thisConnection.withIndex()) {
            val thisField = thisConnection[index.index]
            val thatField = thatConnection[index.index]
            space.fieldController.addConnection(thisField, thatField)
        }
    }

}