package de.bitb.spacerace.model.space.groups

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.space.fields.SpaceField
import javax.inject.Inject

open class SpaceGroup(val offsetX: Float = 0f, val offsetY: Float = 0f) {

    @Inject
    protected lateinit var fieldController: FieldController

    private val connectionPoint: MutableMap<ConnectionPoint, MutableList<SpaceField>> = HashMap()
    val fields: MutableMap<Int, SpaceField> = HashMap()

    init {
        MainGame.appComponent.inject(this)
    }

    fun getField(id: Int): SpaceField {
        return fields[id]!!
    }

    fun addField(addField: SpaceField, anchorField: SpaceField, horizontalMod: Float = 0f, verticalMod: Float = 0f, connection: ConnectionPoint = ConnectionPoint.NONE) {
        val posX = anchorField.getGameImage().x - offsetX + addField.getGameImage().width * horizontalMod
        val posY = anchorField.getGameImage().y - offsetY + addField.getGameImage().height * verticalMod
        addField(addField, posX, posY, connection)
    }

    open fun addField(addField: SpaceField, posX: Float = addField.getGameImage().x, posY: Float = addField.getGameImage().y, connection: ConnectionPoint = ConnectionPoint.NONE) {
        addField.id = fields.size
        addField.setPosition(posX + offsetX, posY + offsetY)
        fields[addField.id] = addField
        if (connection != ConnectionPoint.NONE) {
            addConnectionPoint(connection, addField)
        }
    }

    fun connect(spaceField1: SpaceField, spaceField2: SpaceField) {
        fieldController.addConnection(spaceField1, spaceField2)
    }

    private fun getConnectionPoint(connection: ConnectionPoint): MutableList<SpaceField> {
        var list = connectionPoint[connection]
        if (list == null) {
            list = ArrayList()
            connectionPoint[connection] = list
        }
        return list
    }

    fun addConnectionPoint(connection: ConnectionPoint, field: SpaceField) {
        getConnectionPoint(connection).add(field)
    }

    fun connect(connection: ConnectionPoint, group: SpaceGroup) {
        val thisConnection = getConnectionPoint(connection)
        val thatConnection = group.getConnectionPoint(connection.getOpposite())
        for (index in thisConnection.withIndex()) {
            if (index.index < thatConnection.size) {
                val thisField = thisConnection[index.index]
                val thatField = thatConnection[index.index]
                fieldController.addConnection(thisField, thatField)
            }
        }
    }

}