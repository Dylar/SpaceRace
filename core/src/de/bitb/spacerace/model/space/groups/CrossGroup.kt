package de.bitb.spacerace.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.SpaceField

class CrossGroup(gameController: GameController, offsetX: Float = 0f, offsetY: Float = 0f) : SpaceGroup(gameController, offsetX, offsetY) {

    val screenWidth = SCREEN_WIDTH
    val screenHeight = SCREEN_HEIGHT

    init {
        val normalMod = 1.4f
        val smallMod = 0.9f

        //CENTER
        val giftField1 = SpaceField(fields.size, FieldType.GIFT)
        addField(giftField1, giftField1, screenWidth / 2 - giftField1.width / 2, screenHeight / 2 - giftField1.height / 2)

        //LEFT
        val mineField = MineField(fields.size)
        addField(mineField, giftField1, -normalMod)

        val winField = SpaceField(fields.size, FieldType.WIN)
        addField(winField, mineField, -normalMod)

        gameController.fieldController.addConnection(giftField1, mineField)
        gameController.fieldController.addConnection(mineField, winField)
        addConnectionPoint(ConnectionPoint.LEFT, winField)

        //RIGHT
        val ambushField = SpaceField(fields.size, FieldType.AMBUSH)
        addField(ambushField, giftField1, normalMod)
        val shopField = SpaceField(fields.size, FieldType.SHOP)
        addField(shopField, ambushField, normalMod)

        gameController.fieldController.addConnection(giftField1, ambushField)
        gameController.fieldController.addConnection(ambushField, shopField)
        addConnectionPoint(ConnectionPoint.RIGHT, shopField)

        //DOWN
        var lose1Field = SpaceField(fields.size, FieldType.LOSE)
        addField(lose1Field, giftField1, smallMod, -normalMod)
        var lose2Field = SpaceField(fields.size, FieldType.LOSE)
        addField(lose2Field, lose1Field, smallMod, -normalMod)

        gameController.fieldController.addConnection(giftField1, lose1Field)
        gameController.fieldController.addConnection(lose1Field, lose2Field)
        addConnectionPoint(ConnectionPoint.DOWN, lose2Field)

        var win1Field = SpaceField(fields.size, FieldType.WIN)
        addField(win1Field, giftField1, smallMod, normalMod)
        var win2Field = SpaceField(fields.size, FieldType.WIN)
        addField(win2Field, win1Field, smallMod, normalMod)

        gameController.fieldController.addConnection(giftField1, win1Field)
        gameController.fieldController.addConnection(win1Field, win2Field)
        addConnectionPoint(ConnectionPoint.DOWN, win2Field)

        setPosition(offsetX, offsetY)

        //UP
        lose1Field = SpaceField(fields.size, FieldType.WIN)
        addField(lose1Field, giftField1, -smallMod, normalMod)
        lose2Field = SpaceField(fields.size, FieldType.WIN)
        addField(lose2Field, lose1Field, -smallMod, normalMod)

        gameController.fieldController.addConnection(giftField1, lose1Field)
        gameController.fieldController.addConnection(lose1Field, lose2Field)
        addConnectionPoint(ConnectionPoint.UP, lose2Field)

        win1Field = SpaceField(fields.size, FieldType.LOSE)
        addField(win1Field, giftField1, smallMod, normalMod)
        win2Field = SpaceField(fields.size, FieldType.LOSE)
        addField(win2Field, win1Field, smallMod, normalMod)

        gameController.fieldController.addConnection(giftField1, win1Field)
        gameController.fieldController.addConnection(win1Field, win2Field)
        addConnectionPoint(ConnectionPoint.UP, win2Field)

        setPosition(offsetX, offsetY)
    }


}