package de.bitb.spacerace.grafik.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_LARGE
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_TOO_LARGE
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_XXLARGE
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.grafik.model.enums.ConnectionPoint
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic

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
        val centerBottomField = FieldGraphic.createField(fieldType[index])
/*0*/   addField(centerBottomField, SCREEN_WIDTH / 2)
        val leftBottomCorner = FieldGraphic.createField(fieldType[index])
/*1*/   addField(leftBottomCorner, centerBottomField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)
        val rightBottomCorner = FieldGraphic.createField(fieldType[index])
/*2*/   addField(rightBottomCorner, centerBottomField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)

        connectFields(leftBottomCorner, centerBottomField)
        connectFields(rightBottomCorner, centerBottomField)

        //TOP
        val centerTopField = FieldGraphic.createField(fieldType[index])
/*3*/   addField(centerTopField, centerBottomField, verticalMod = FIELD_PADDING_XXLARGE)
        val leftTopCorner = FieldGraphic.createField(fieldType[index])
/*4*/   addField(leftTopCorner, centerTopField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)
        val rightTopCorner = FieldGraphic.createField(fieldType[index])
/*5*/   addField(rightTopCorner, centerTopField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)

        connectFields(leftTopCorner, centerTopField)
        connectFields(rightTopCorner, centerTopField)

        //CENTER
        val leftCenterField = FieldGraphic.createField(fieldType[index])
/*6*/   addField(leftCenterField, centerBottomField, -FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.LEFT)
        val rightCenterField = FieldGraphic.createField(fieldType[index])
/*7*/   addField(rightCenterField, centerBottomField, FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.RIGHT)

        //LONG WAY
        val longWayField = FieldGraphic.createField(fieldType[index])
/*8*/   addField(longWayField, centerBottomField, verticalMod = -FIELD_PADDING_TOO_LARGE, connection = ConnectionPoint.TOP)
//        addConnectionPoint(ConnectionPoint.BOTTOM, longWayField)
//        addConnectionPoint(ConnectionPoint.LEFT, longWayField)
//        addConnectionPoint(ConnectionPoint.RIGHT, longWayField)
        val moonField = FieldGraphic.createField(fieldType[index])
        addField(moonField, leftBottomCorner, FIELD_PADDING_LARGE)

        moonField.fieldImage.setRotating(moonField, leftBottomCorner.getGameImage(), (moonField.getGameImage().height * FIELD_PADDING_XXLARGE).toDouble())

        connectFields(longWayField, leftBottomCorner)
        connectFields(leftBottomCorner, moonField)

        connectFields(leftCenterField, rightCenterField)

        connectFields(leftCenterField, centerBottomField)
        connectFields(rightCenterField, rightTopCorner)
        connectFields(leftTopCorner, leftBottomCorner)
        connectFields(rightTopCorner, rightBottomCorner)

    }

    override fun addField(addField: FieldGraphic, posX: Float, posY: Float, connection: ConnectionPoint) {
        super.addField(addField, posX, posY, connection)
        index++
        if (fieldTypeSize == index) index = 0
    }

}