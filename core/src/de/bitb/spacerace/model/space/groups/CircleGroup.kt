package de.bitb.spacerace.model.space.groups

import de.bitb.spacerace.config.*
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.SpaceField

class CircleGroup(gameController: GameController, offsetX: Float = 0f, offsetY: Float = 0f) : SpaceGroup(gameController, offsetX, offsetY) {

    init {

        //BOTTOM
        val centerBottomField = SpaceField(FieldType.LOSE)
        addField(centerBottomField, SCREEN_WIDTH / 2 - centerBottomField.width / 2)
        val leftBottomCorner = SpaceField(FieldType.LOSE)
        addField(leftBottomCorner, centerBottomField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)
        val rightBottomCorner = SpaceField(FieldType.WIN)
        addField(rightBottomCorner, centerBottomField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)

        connect(leftBottomCorner, centerBottomField)
        connect(rightBottomCorner, centerBottomField)

        //TOP
        val centerTopField = SpaceField(FieldType.LOSE)
        addField(centerTopField, centerBottomField, verticalMod = FIELD_PADDING_XXLARGE)
        val leftTopCorner = SpaceField(FieldType.SHOP)
        addField(leftTopCorner, centerTopField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)
        val rightTopCorner = MineField()
        addField(rightTopCorner, centerTopField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)

        connect(leftTopCorner, centerTopField)
        connect(rightTopCorner, centerTopField)

        //CENTER
        val leftCenterField = SpaceField(FieldType.GIFT)
        addField(leftCenterField, centerBottomField, -FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.LEFT)
        val rightCenterField = SpaceField(FieldType.LOSE)
        addField(rightCenterField, centerBottomField, FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.RIGHT)

        connect(leftCenterField, rightCenterField)

        connect(leftCenterField, centerBottomField)
        connect(rightCenterField, rightTopCorner)
        connect(leftTopCorner, leftBottomCorner)
        connect(rightTopCorner, rightBottomCorner)

    }

}