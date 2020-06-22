package de.bitb.spacerace.grafik.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_LARGE
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_PADDING_MEDIUM
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT_HALF
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH_HALF
import de.bitb.spacerace.grafik.model.enums.ConnectionPoint
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic

class CrossGroup(offsetX: Float = 0f,
                 offsetY: Float = 0f,
                 starMode: Boolean = false
) : SpaceGroup(offsetX, offsetY) {

    init {

        //CENTER
        val centerField = FieldGraphic.createFieldOLD(FieldType.GIFT)
        addField(centerField, SCREEN_WIDTH_HALF, SCREEN_HEIGHT_HALF)

        //LEFT
        val left1Field = FieldGraphic.createFieldOLD(FieldType.MINE)
        addField(left1Field, centerField, -FIELD_PADDING_LARGE)
        val left2Field = FieldGraphic.createFieldOLD(FieldType.WIN)
        addField(left2Field, left1Field, -FIELD_PADDING_LARGE, connection = ConnectionPoint.LEFT)

        connectFields(centerField, left1Field)
        connectFields(left1Field, left2Field)

        //RIGHT
        val right1Field = FieldGraphic.createFieldOLD(FieldType.AMBUSH)
        addField(right1Field, centerField, FIELD_PADDING_LARGE)
        val right2Field = FieldGraphic.createFieldOLD(FieldType.SHOP)
        addField(right2Field, right1Field, FIELD_PADDING_LARGE, connection = ConnectionPoint.RIGHT)

        connectFields(centerField, right1Field)
        connectFields(right1Field, right2Field)

        //BOTTOM
        val leftDown1Field = FieldGraphic.createFieldOLD(FieldType.LOSE)
        addField(leftDown1Field, centerField, -FIELD_PADDING_MEDIUM, -FIELD_PADDING_LARGE)
        val leftDown2Field = FieldGraphic.createFieldOLD(FieldType.WIN)
        addField(leftDown2Field, leftDown1Field, if (starMode) -FIELD_PADDING_MEDIUM else 0f, -FIELD_PADDING_LARGE, ConnectionPoint.BOTTOM)
        val rightDown1Field = FieldGraphic.createFieldOLD(FieldType.LOSE)
        addField(rightDown1Field, centerField, FIELD_PADDING_MEDIUM, -FIELD_PADDING_LARGE)
        val rightDown2Field = FieldGraphic.createFieldOLD(FieldType.WIN)
        addField(rightDown2Field, rightDown1Field, if (starMode) FIELD_PADDING_MEDIUM else 0f, -FIELD_PADDING_LARGE, ConnectionPoint.BOTTOM)

        connectFields(centerField, rightDown1Field)
        connectFields(rightDown1Field, rightDown2Field)
        connectFields(centerField, leftDown1Field)
        connectFields(leftDown1Field, leftDown2Field)

        //TOP
        val leftUp1Field = FieldGraphic.createFieldOLD(FieldType.LOSE)
        addField(leftUp1Field, centerField, -FIELD_PADDING_MEDIUM, FIELD_PADDING_LARGE)
        val leftUp2Field = FieldGraphic.createFieldOLD(FieldType.WIN)
        addField(leftUp2Field, leftUp1Field, if (starMode) -FIELD_PADDING_MEDIUM else 0f, FIELD_PADDING_LARGE, ConnectionPoint.TOP)
        val rightUp1Field = FieldGraphic.createFieldOLD(FieldType.LOSE)
        addField(rightUp1Field, centerField, FIELD_PADDING_MEDIUM, FIELD_PADDING_LARGE)
        val rightUp2Field = FieldGraphic.createFieldOLD(FieldType.WIN)
        addField(rightUp2Field, rightUp1Field, if (starMode) FIELD_PADDING_MEDIUM else 0f, FIELD_PADDING_LARGE, ConnectionPoint.TOP)

        connectFields(centerField, leftUp1Field)
        connectFields(leftUp1Field, leftUp2Field)
        connectFields(centerField, rightUp1Field)
        connectFields(rightUp1Field, rightUp2Field)
    }
}