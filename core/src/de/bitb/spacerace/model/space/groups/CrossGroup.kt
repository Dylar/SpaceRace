package de.bitb.spacerace.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_LARGE
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_MEDIUM
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT_HALF
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH_HALF
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.space.fields.SpaceField

class CrossGroup(gameController: GameController, offsetX: Float = 0f, offsetY: Float = 0f, starMode: Boolean = false) : SpaceGroup(gameController, offsetX, offsetY) {

    init {

        //CENTER
        val centerField = SpaceField.createField(FieldType.GIFT)
        addField(centerField, SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF)

        //LEFT
        val left1Field = SpaceField.createField(FieldType.MINE)
        addField(left1Field, centerField, -FIELD_PADDING_LARGE)
        val left2Field = SpaceField.createField(FieldType.WIN)
        addField(left2Field, left1Field, -FIELD_PADDING_LARGE, connection = ConnectionPoint.LEFT)

        connect(centerField, left1Field)
        connect(left1Field, left2Field)

        //RIGHT
        val right1Field = SpaceField.createField(FieldType.AMBUSH)
        addField(right1Field, centerField, FIELD_PADDING_LARGE)
        val right2Field = SpaceField.createField(FieldType.SHOP)
        addField(right2Field, right1Field, FIELD_PADDING_LARGE, connection = ConnectionPoint.RIGHT)

        connect(centerField, right1Field)
        connect(right1Field, right2Field)

        //BOTTOM
        val leftDown1Field = SpaceField.createField(FieldType.LOSE)
        addField(leftDown1Field, centerField, -FIELD_PADDING_MEDIUM, -FIELD_PADDING_LARGE)
        val leftDown2Field = SpaceField.createField(FieldType.WIN)
        addField(leftDown2Field, leftDown1Field, if (starMode) -FIELD_PADDING_MEDIUM else 0f, -FIELD_PADDING_LARGE, ConnectionPoint.BOTTOM)
        val rightDown1Field = SpaceField.createField(FieldType.LOSE)
        addField(rightDown1Field, centerField, FIELD_PADDING_MEDIUM, -FIELD_PADDING_LARGE)
        val rightDown2Field = SpaceField.createField(FieldType.WIN)
        addField(rightDown2Field, rightDown1Field, if (starMode) FIELD_PADDING_MEDIUM else 0f, -FIELD_PADDING_LARGE, ConnectionPoint.BOTTOM)

        connect(centerField, rightDown1Field)
        connect(rightDown1Field, rightDown2Field)
        connect(centerField, leftDown1Field)
        connect(leftDown1Field, leftDown2Field)

        //TOP
        val leftUp1Field = SpaceField.createField(FieldType.LOSE)
        addField(leftUp1Field, centerField, -FIELD_PADDING_MEDIUM, FIELD_PADDING_LARGE)
        val leftUp2Field = SpaceField.createField(FieldType.WIN)
        addField(leftUp2Field, leftUp1Field, if (starMode) -FIELD_PADDING_MEDIUM else 0f, FIELD_PADDING_LARGE, ConnectionPoint.TOP)
        val rightUp1Field = SpaceField.createField(FieldType.LOSE)
        addField(rightUp1Field, centerField, FIELD_PADDING_MEDIUM, FIELD_PADDING_LARGE)
        val rightUp2Field = SpaceField.createField(FieldType.WIN)
        addField(rightUp2Field, rightUp1Field, if (starMode) FIELD_PADDING_MEDIUM else 0f, FIELD_PADDING_LARGE, ConnectionPoint.TOP)

        connect(centerField, leftUp1Field)
        connect(leftUp1Field, leftUp2Field)
        connect(centerField, rightUp1Field)
        connect(rightUp1Field, rightUp2Field)
    }
}