package de.bitb.spacerace.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_LARGE
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_XXLARGE
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.fields.SpaceField

class AsteroidGroup(offsetX: Float = 0f, offsetY: Float = 0f) : SpaceGroup(offsetX, offsetY) {

    init {

        //BOTTOM
        val centerBottomField = SpaceField.createField(FieldType.AMBUSH)
        addField(centerBottomField, SCREEN_WIDTH / 2)
        val leftBottomCorner = SpaceField.createField(FieldType.AMBUSH)
        addField(leftBottomCorner, centerBottomField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)
        val rightBottomCorner = SpaceField.createField(FieldType.AMBUSH)
        addField(rightBottomCorner, centerBottomField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)

        connectFields(leftBottomCorner, centerBottomField)
        connectFields(rightBottomCorner, centerBottomField)

        //TOP
        val centerTopField = SpaceField.createField(FieldType.AMBUSH)
        addField(centerTopField, centerBottomField, verticalMod = FIELD_PADDING_XXLARGE)
        val leftTopCorner = SpaceField.createField(FieldType.AMBUSH)
        addField(leftTopCorner, centerTopField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)
        val rightTopCorner = SpaceField.createField(FieldType.AMBUSH)
        addField(rightTopCorner, centerTopField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)

        connectFields(leftTopCorner, centerTopField)
        connectFields(rightTopCorner, centerTopField)

        //CENTER
        val leftCenterField = SpaceField.createField(FieldType.AMBUSH)
        addField(leftCenterField, centerBottomField, -FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.LEFT)
        val rightCenterField = SpaceField.createField(FieldType.AMBUSH)
        addField(rightCenterField, centerBottomField, FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.RIGHT)

        connectFields(leftCenterField, rightCenterField)

        connectFields(leftCenterField, centerBottomField)
        connectFields(rightCenterField, rightTopCorner)
        connectFields(leftTopCorner, leftBottomCorner)
        connectFields(rightTopCorner, rightBottomCorner)

    }

}