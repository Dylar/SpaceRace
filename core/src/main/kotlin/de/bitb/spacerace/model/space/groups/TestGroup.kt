package de.bitb.spacerace.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_LARGE
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_TOO_LARGE
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_XXLARGE
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.fields.SpaceField

open class TestGroup(
        offsetX: Float = 0f,
        offsetY: Float = 0f,
        vararg fieldType: FieldType
) : SpaceGroup(offsetX, offsetY) {
    var fieldTypeSize = fieldType.size
    var index = 0

    init {
        //BOTTOM
        fieldTypeSize = fieldType.size
        val centerBottomField = SpaceField.createField(fieldType[index])
/*0*/   addField(centerBottomField, SCREEN_WIDTH / 2)
        val leftBottomCorner = SpaceField.createField(fieldType[index])
/*1*/   addField(leftBottomCorner, centerBottomField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)
        val rightBottomCorner = SpaceField.createField(fieldType[index])
/*2*/   addField(rightBottomCorner, centerBottomField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)

        connect(leftBottomCorner, centerBottomField)
        connect(rightBottomCorner, centerBottomField)

        //TOP
        val centerTopField = SpaceField.createField(fieldType[index])
/*3*/   addField(centerTopField, centerBottomField, verticalMod = FIELD_PADDING_XXLARGE)
        val leftTopCorner = SpaceField.createField(fieldType[index])
/*4*/   addField(leftTopCorner, centerTopField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)
        val rightTopCorner = SpaceField.createField(fieldType[index])
/*5*/   addField(rightTopCorner, centerTopField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)

        connect(leftTopCorner, centerTopField)
        connect(rightTopCorner, centerTopField)

        //CENTER
        val leftCenterField = SpaceField.createField(fieldType[index])
/*6*/   addField(leftCenterField, centerBottomField, -FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.LEFT)
        val rightCenterField = SpaceField.createField(fieldType[index])
/*7*/   addField(rightCenterField, centerBottomField, FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.RIGHT)

        //LONG WAY
        val longWayField = SpaceField.createField(fieldType[index])
/*8*/   addField(longWayField, centerBottomField, verticalMod = -FIELD_PADDING_TOO_LARGE, connection = ConnectionPoint.TOP)
//        addConnectionPoint(ConnectionPoint.BOTTOM, longWayField)
//        addConnectionPoint(ConnectionPoint.LEFT, longWayField)
//        addConnectionPoint(ConnectionPoint.RIGHT, longWayField)
        val moonField = SpaceField.createField(fieldType[index])
        addField(moonField, leftBottomCorner, FIELD_PADDING_LARGE)

        moonField.fieldImage.setRotating(moonField, leftBottomCorner.getGameImage(), (moonField.getGameImage().height * FIELD_PADDING_XXLARGE).toDouble())

        connect(longWayField, leftBottomCorner)
        connect(leftBottomCorner, moonField)

        connect(leftCenterField, rightCenterField)

        connect(leftCenterField, centerBottomField)
        connect(rightCenterField, rightTopCorner)
        connect(leftTopCorner, leftBottomCorner)
        connect(rightTopCorner, rightBottomCorner)

    }

    override fun addField(addField: SpaceField, posX: Float, posY: Float, connection: ConnectionPoint) {
        super.addField(addField, posX, posY, connection)
        index++
        if (fieldTypeSize == index) index = 0
    }

}