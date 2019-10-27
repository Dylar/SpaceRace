package de.bitb.spacerace.grafik.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_XXLARGE
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT_HALF
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH_HALF
import de.bitb.spacerace.grafik.model.enums.ConnectionPoint
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic

open class SpinningGroup(offsetX: Float = 0f,
                         offsetY: Float = 0f,
                         vararg fieldType: FieldType
) : SpaceGroup(offsetX, offsetY) {
    var fieldTypeSize = fieldType.size
    var index = 0

    init {
        val distance = FIELD_PADDING_XXLARGE
        val radius = (FIELD_BORDER * 2 * distance).toDouble()
        //CENTER

        fieldTypeSize = fieldType.size
        val centerPoint = FieldGraphic.createField(fieldType[index])
        addField(centerPoint, SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF)

//        solarMap(fieldType, distance, radius)
//        testPlanet(centerPoint, radius,fieldType)
        testSnake(centerPoint, radius, fieldType)
    }

    private fun solarMap(fieldType: Array<out FieldType>, distance: Float, radius: Double) {

        val centerPoint = FieldGraphic.createField(fieldType[index])
        addField(centerPoint, SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF)

        val leftPoint = FieldGraphic.createField(fieldType[index])
        addField(leftPoint, centerPoint, -distance, connection = ConnectionPoint.LEFT)

        val rightPoint = FieldGraphic.createField(fieldType[index])
        addField(rightPoint, centerPoint, distance, connection = ConnectionPoint.RIGHT)

        val topPoint = FieldGraphic.createField(fieldType[index])
        addField(topPoint, centerPoint, verticalMod = -distance, connection = ConnectionPoint.TOP)

        val bottomPoint = FieldGraphic.createField(fieldType[index])
        addField(bottomPoint, centerPoint, verticalMod = distance, connection = ConnectionPoint.BOTTOM)

        connectFields(centerPoint, leftPoint)
        connectFields(centerPoint, rightPoint)
        connectFields(centerPoint, topPoint)
        connectFields(centerPoint, bottomPoint)

        val moonField1 = FieldGraphic.createField(fieldType[index])
        addMoon(moonField1, centerPoint, radius)
        connectFields(centerPoint, moonField1)
    }

    private fun testPlanet(centerPoint: FieldGraphic, radius: Double, fieldType: Array<out FieldType>) {
        val planet1 = FieldGraphic.createField(fieldType[index])
        addMoon(planet1, centerPoint, radius * 1.0)

        val planet2 = FieldGraphic.createField(fieldType[index])
        addMoon(planet2, centerPoint, radius * 2.0)

        val planet3 = FieldGraphic.createField(fieldType[index])
        addMoon(planet3, centerPoint, radius * 3.0)

        connectFields(centerPoint, planet1)
        connectFields(centerPoint, planet2)
        connectFields(centerPoint, planet3)
    }

    private fun testSnake(centerPoint: FieldGraphic, radius: Double, fieldType: Array<out FieldType>) {
        var center = centerPoint
        for (i in 0..10) {
            val moon = FieldGraphic.createField(fieldType[index])
            moon.gamePosition.posX = i.toFloat()
            addMoon(moon, center, radius)
            connectFields(center, moon)
            center = moon
        }

    }

    private fun addMoon(moonField: FieldGraphic, centerPoint: FieldGraphic, radius: Double) {
        addField(moonField, centerPoint)
        moonField.fieldImage.setRotating(moonField, centerPoint.getGameImage(), radius)
    }

    override fun addField(addField: FieldGraphic, posX: Float, posY: Float, connection: ConnectionPoint) {
        super.addField(addField, posX, posY, connection)
        index++
        if (fieldTypeSize == index) index = 0
    }

}