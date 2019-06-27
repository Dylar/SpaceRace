package de.bitb.spacerace.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_XXLARGE
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT_HALF
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH_HALF
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.fields.SpaceField

open class SpinningGroup(gameController: GameController, offsetX: Float = 0f, offsetY: Float = 0f, vararg fieldType: FieldType) : SpaceGroup(gameController, offsetX, offsetY) {
    var fieldTypeSize = fieldType.size
    var index = 0

    init {
        val distance = FIELD_PADDING_XXLARGE
        val radius = (FIELD_BORDER * 2 * distance).toDouble()
        //CENTER

        fieldTypeSize = fieldType.size
        val centerPoint = SpaceField.createField(fieldType[index])
        addField(centerPoint, SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF)

//        solarMap(fieldType, distance, radius)
//        testPlanet(centerPoint, radius,fieldType)
        testSnake(centerPoint, radius, fieldType)
    }

    private fun solarMap(fieldType: Array<out FieldType>, distance: Float, radius: Double) {

        val centerPoint = SpaceField.createField(fieldType[index])
        addField(centerPoint, SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF)

        val leftPoint = SpaceField.createField(fieldType[index])
        addField(leftPoint, centerPoint, -distance, connection = ConnectionPoint.LEFT)

        val rightPoint = SpaceField.createField(fieldType[index])
        addField(rightPoint, centerPoint, distance, connection = ConnectionPoint.RIGHT)

        val topPoint = SpaceField.createField(fieldType[index])
        addField(topPoint, centerPoint, verticalMod = -distance, connection = ConnectionPoint.TOP)

        val bottomPoint = SpaceField.createField(fieldType[index])
        addField(bottomPoint, centerPoint, verticalMod = distance, connection = ConnectionPoint.BOTTOM)

        connect(centerPoint, leftPoint)
        connect(centerPoint, rightPoint)
        connect(centerPoint, topPoint)
        connect(centerPoint, bottomPoint)

        val moonField1 = SpaceField.createField(fieldType[index])
        addMoon(moonField1, centerPoint, radius)
        connect(centerPoint, moonField1)
    }

    private fun testPlanet(centerPoint: SpaceField, radius: Double, fieldType: Array<out FieldType>) {
        val planet1 = SpaceField.createField(fieldType[index])
        addMoon(planet1, centerPoint, radius * 1.0)

        val planet2 = SpaceField.createField(fieldType[index])
        addMoon(planet2, centerPoint, radius * 2.0)

        val planet3 = SpaceField.createField(fieldType[index])
        addMoon(planet3, centerPoint, radius * 3.0)

        connect(centerPoint, planet1)
        connect(centerPoint, planet2)
        connect(centerPoint, planet3)
    }

    private fun testSnake(centerPoint: SpaceField, radius: Double, fieldType: Array<out FieldType>) {
        var center = centerPoint
        for (i in 0..10) {
            val moon = SpaceField.createField(fieldType[index])
            moon.gamePosition.posX = i.toFloat()
            addMoon(moon, center, radius)
            connect(center, moon)
            center = moon
        }

    }

    private fun addMoon(moonField: SpaceField, centerPoint: SpaceField, radius: Double) {
        addField(moonField, centerPoint)
        moonField.fieldImage.setRotating(moonField, centerPoint.getGameImage(), radius)
    }

    override fun addField(addField: SpaceField, posX: Float, posY: Float, connection: ConnectionPoint) {
        super.addField(addField, posX, posY, connection)
        index++
        if (fieldTypeSize == index) index = 0
    }

}