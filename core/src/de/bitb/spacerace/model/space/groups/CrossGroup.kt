package de.bitb.spacerace.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.SpaceField

class CrossGroup(gameController: GameController, offsetX: Float = 0f, offsetY: Float = 0f, starMode: Boolean = false) : SpaceGroup(gameController, offsetX, offsetY) {

    val screenWidth = SCREEN_WIDTH
    val screenHeight = SCREEN_HEIGHT

    init {
        val largeMod = 1.2f
        val mediumMod = 0.7f
        val smallMod = 0.3f


        //CENTER
        val centerField = SpaceField(FieldType.GIFT)
        addField(centerField, screenWidth / 2 - centerField.width / 2, screenHeight / 2 - centerField.height / 2)

        //LEFT
        val left1Field = MineField()
        addField(left1Field, centerField, -largeMod)

        val left2Field = SpaceField(FieldType.WIN)
        addField(left2Field, left1Field, -largeMod, connection = ConnectionPoint.LEFT)

        gameController.fieldController.addConnection(centerField, left1Field)
        gameController.fieldController.addConnection(left1Field, left2Field)

        //RIGHT
        val right1Field = SpaceField(FieldType.AMBUSH)
        addField(right1Field, centerField, largeMod)
        val right2Field = SpaceField(FieldType.SHOP)
        addField(right2Field, right1Field, largeMod, connection = ConnectionPoint.RIGHT)

        gameController.fieldController.addConnection(centerField, right1Field)
        gameController.fieldController.addConnection(right1Field, right2Field)

        //DOWN
        val rightDown1Field = SpaceField(FieldType.LOSE)
        addField(rightDown1Field, centerField, mediumMod, -largeMod)
        val rightDown2Field = SpaceField(FieldType.WIN)
        addField(rightDown2Field, rightDown1Field, if (starMode) mediumMod else 0f, -largeMod, ConnectionPoint.DOWN)

        gameController.fieldController.addConnection(centerField, rightDown1Field)
        gameController.fieldController.addConnection(rightDown1Field, rightDown2Field)

        val leftDown1Field = SpaceField(FieldType.LOSE)
        addField(leftDown1Field, centerField, -mediumMod, -largeMod)
        val leftDown2Field = SpaceField(FieldType.WIN)
        addField(leftDown2Field, leftDown1Field, if (starMode) -mediumMod else 0f, -largeMod, ConnectionPoint.DOWN)

        gameController.fieldController.addConnection(centerField, leftDown1Field)
        gameController.fieldController.addConnection(leftDown1Field, leftDown2Field)

        //UP
        val rightUp1Field = SpaceField(FieldType.LOSE)
        addField(rightUp1Field, centerField, mediumMod, largeMod)
        val rightUp2Field = SpaceField(FieldType.WIN)
        addField(rightUp2Field, rightUp1Field, if (starMode) mediumMod else 0f, largeMod, ConnectionPoint.UP)

        gameController.fieldController.addConnection(centerField, rightUp1Field)
        gameController.fieldController.addConnection(rightUp1Field, rightUp2Field)

        val leftUp1Field = SpaceField(FieldType.LOSE)
        addField(leftUp1Field, centerField, -mediumMod, largeMod)
        val leftUp2Field = SpaceField(FieldType.WIN)
        addField(leftUp2Field, leftUp1Field, if (starMode) -mediumMod else 0f, largeMod, ConnectionPoint.UP)

        gameController.fieldController.addConnection(centerField, leftUp1Field)
        gameController.fieldController.addConnection(leftUp1Field, leftUp2Field)

        setPosition(offsetX, offsetY)
    }


}