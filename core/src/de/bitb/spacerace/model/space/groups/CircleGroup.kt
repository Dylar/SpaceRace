package de.bitb.spacerace.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.fields.SpaceField


open class CircleGroup(gameController: GameController,
                       offsetX: Float = 0f,
                       offsetY: Float = 0f,
                       fieldTypes: List<FieldType>) : SpaceGroup(gameController, offsetX, offsetY) {

    init {
        val size = fieldTypes.size
        val slice = 2 * Math.PI / size
        val radius = SCREEN_HEIGHT * 0.6


        val connectionFields = ArrayList<SpaceField>()
        var firstField: SpaceField? = null
        var anchorField: SpaceField? = null
        var addField: SpaceField? = null
        for (fieldType in fieldTypes.withIndex()) {

            val angle = slice * fieldType.index
            val newX: Double = (SCREEN_WIDTH / 2 + radius * Math.cos(angle))
            val newY: Double = (SCREEN_HEIGHT / 2 + radius * Math.sin(angle))

            addField = SpaceField.createField(fieldType.value)
            addField(addField, newX.toFloat(), newY.toFloat())
            if (anchorField != null) {
                connect(anchorField, addField)
            }

            if (angle >= slice * size / 4 * connectionFields.size) {
                connectionFields.add(addField)
            }

            anchorField = addField

            if (firstField == null) {
                firstField = addField
            }
        }

        connect(firstField!!, addField!!)

        if (connectionFields.size > 0)
            addConnectionPoint(ConnectionPoint.RIGHT, connectionFields[0])
        if (connectionFields.size > 1)
            addConnectionPoint(ConnectionPoint.TOP, connectionFields[1])
        if (connectionFields.size > 2)
            addConnectionPoint(ConnectionPoint.LEFT, connectionFields[2])
        if (connectionFields.size > 3)
            addConnectionPoint(ConnectionPoint.BOTTOM, connectionFields[3])

    }
}