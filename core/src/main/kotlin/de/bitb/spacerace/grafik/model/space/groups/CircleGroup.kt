package de.bitb.spacerace.grafik.model.space.groups

import com.badlogic.gdx.math.Vector2
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.grafik.model.enums.ConnectionPoint
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic
import de.bitb.spacerace.core.utils.CalculationUtils


open class CircleGroup(
        offsetX: Float = 0f,
        offsetY: Float = 0f,
        fieldTypes: List<FieldType>
) : SpaceGroup(offsetX, offsetY) {

    init {
        val size = fieldTypes.size
        val slice = 2 * Math.PI / size
        val radius = SCREEN_HEIGHT * 0.6
        val rotationPoint = Vector2((SCREEN_WIDTH / 2).toFloat() + offsetX, (SCREEN_HEIGHT / 2).toFloat() + offsetY)


        val connectionFields = ArrayList<FieldGraphic>()
        var firstField: FieldGraphic? = null
        var anchorField: FieldGraphic? = null
        var addField: FieldGraphic? = null
        for (fieldType in fieldTypes.withIndex()) {

            val angle = slice * fieldType.index
            val point = CalculationUtils.calculateRotationPoint(rotationPoint, radius, angle)

            addField = FieldGraphic.createField(fieldType.value)

            if (anchorField != null) {
                connectFields(anchorField, addField)
            }

            addField(addField, point.x, point.y)
            anchorField = addField

            if (angle >= slice * size / 4 * connectionFields.size) {
                connectionFields.add(addField)
            }


            if (firstField == null) {
                firstField = addField
            }
        }

        connectFields(firstField!!, addField!!)

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