package de.bitb.spacerace.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_LARGE
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_XXLARGE
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.SpaceField

class RandomGroup(gameController: GameController, offsetX: Float = 0f, offsetY: Float = 0f) : SpaceGroup(gameController, offsetX, offsetY) {

    init {

        //BOTTOM
        val centerBottomField = SpaceField.createField(FieldType.RANDOM)
        addField(centerBottomField, SCREEN_WIDTH / 2 - centerBottomField.width / 2)
        val leftBottomCorner = SpaceField.createField(FieldType.RANDOM)
        addField(leftBottomCorner, centerBottomField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)
        val rightBottomCorner = SpaceField.createField(FieldType.RANDOM)
        addField(rightBottomCorner, centerBottomField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.BOTTOM)

        connect(leftBottomCorner, centerBottomField)
        connect(rightBottomCorner, centerBottomField)

        //TOP
        val centerTopField = SpaceField.createField(FieldType.RANDOM)
        addField(centerTopField, centerBottomField, verticalMod = FIELD_PADDING_XXLARGE)
        val leftTopCorner = SpaceField.createField(FieldType.RANDOM)
        addField(leftTopCorner, centerTopField, -FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)
        val rightTopCorner = SpaceField.createField(FieldType.RANDOM)
        addField(rightTopCorner, centerTopField, FIELD_PADDING_XXLARGE, connection = ConnectionPoint.TOP)

        connect(leftTopCorner, centerTopField)
        connect(rightTopCorner, centerTopField)

        //CENTER
        val leftCenterField = SpaceField.createField(FieldType.RANDOM)
        addField(leftCenterField, centerBottomField, -FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.LEFT)
        val rightCenterField = SpaceField.createField(FieldType.RANDOM)
        addField(rightCenterField, centerBottomField, FIELD_PADDING_LARGE, FIELD_PADDING_LARGE, ConnectionPoint.RIGHT)

        connect(leftCenterField, rightCenterField)

        connect(leftCenterField, centerBottomField)
        connect(rightCenterField, rightTopCorner)
        connect(leftTopCorner, leftBottomCorner)
        connect(rightTopCorner, rightBottomCorner)

    }

}