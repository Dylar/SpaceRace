package de.bitb.spacerace.grafik.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_LARGE
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_XXLARGE
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.grafik.model.enums.ConnectionPoint
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic

class BoxGroup(offsetX: Float = 0f, offsetY: Float = 0f) : SpaceGroup(offsetX, offsetY) {

    init {

        //BOTTOM
        val centerBottomField = FieldGraphic.createField(FieldType.LOSE)
        addField(centerBottomField, SCREEN_WIDTH / 2)
        val leftBottomCorner = FieldGraphic.createField(FieldType.LOSE)
        addField(leftBottomCorner, centerBottomField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)
        val rightBottomCorner = FieldGraphic.createField(FieldType.GOAL)
        addField(rightBottomCorner, centerBottomField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)

        connectFields(leftBottomCorner, centerBottomField)
        connectFields(rightBottomCorner, centerBottomField)

        //TOP
        val centerTopField = FieldGraphic.createField(FieldType.LOSE)
        addField(centerTopField, centerBottomField, verticalMod = FIELD_PADDING_XXLARGE)
        val leftTopCorner = FieldGraphic.createField(FieldType.SHOP)
        addField(leftTopCorner, centerTopField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)
        val rightTopCorner = FieldGraphic.createField(FieldType.MINE)
        addField(rightTopCorner, centerTopField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)

        connectFields(leftTopCorner, centerTopField)
        connectFields(rightTopCorner, centerTopField)

        //CENTER
        val leftCenterField = FieldGraphic.createField(FieldType.GIFT)
        addField(leftCenterField, centerBottomField, -FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.LEFT)
        val rightCenterField = FieldGraphic.createField(FieldType.LOSE)
        addField(rightCenterField, centerBottomField, FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.RIGHT)

        connectFields(leftCenterField, rightCenterField)

        connectFields(leftCenterField, centerBottomField)
        connectFields(rightCenterField, rightTopCorner)
        connectFields(leftTopCorner, leftBottomCorner)
        connectFields(rightTopCorner, rightBottomCorner)

    }

}