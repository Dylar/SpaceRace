package de.bitb.spacerace.model.space.groups

import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.control.GameController
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.model.space.fields.SpaceField

class TestGroup(space: GameController, offsetX: Float = 0f, offsetY: Float = 0f) : SpaceGroup(space, offsetX, offsetY) {

    val screenWidth = SCREEN_WIDTH
    val screenHeight = SCREEN_HEIGHT

    init {

        val leftDownCorner = SpaceField(FieldType.GIFT)
        addField(leftDownCorner)
        

        val spaceField2 = MineField()
        addField(spaceField2, screenWidth - spaceField2.width)
        val spaceField3 = SpaceField(FieldType.LOSE)
        addField(spaceField3, screenWidth / 2 - spaceField3.width / 2)
        val spaceField4 = SpaceField(FieldType.AMBUSH)
        addField(spaceField4, screenWidth - spaceField4.width, screenHeight - spaceField4.height)
        val spaceField5 = SpaceField(FieldType.UNKNOWN)
        addField(spaceField5, screenWidth / 2 - spaceField5.width / 2, screenHeight - spaceField5.height)
        val spaceField6 = SpaceField(FieldType.LOSE)
        addField(spaceField6, posY = screenHeight - spaceField6.height)
        val spaceField7 = SpaceField(FieldType.SHOP)
        addField(spaceField7, ((screenWidth / 3 - spaceField7.width * 2 / 3)), (screenHeight / 2 - spaceField7.height / 2))
        val spaceField8 = SpaceField(FieldType.RANDOM)
        addField(spaceField8, ((screenWidth * 2 / 3 + spaceField8.width / 3)), (screenHeight / 2 - spaceField8.height / 2))

        val spaceField9 = SpaceField(FieldType.WIN)
        addField(spaceField9, screenWidth.toFloat(), posY = -(screenHeight / 2 - spaceField9.height / 2))

        space.fieldController.addConnection(leftDownCorner, spaceField3)
        space.fieldController.addConnection(spaceField2, spaceField3)
        space.fieldController.addConnection(spaceField5, spaceField6)
        space.fieldController.addConnection(spaceField5, spaceField4)
        space.fieldController.addConnection(spaceField8, spaceField4)
        space.fieldController.addConnection(spaceField2, spaceField4)
        space.fieldController.addConnection(spaceField3, spaceField7)
        space.fieldController.addConnection(spaceField8, spaceField7)
        space.fieldController.addConnection(leftDownCorner, spaceField6)
        space.fieldController.addConnection(spaceField7, spaceField6)
        space.fieldController.addConnection(spaceField2, spaceField9)

        addConnectionPoint(ConnectionPoint.UP, spaceField5)
        addConnectionPoint(ConnectionPoint.DOWN, spaceField3)
        addConnectionPoint(ConnectionPoint.LEFT, spaceField7)
        addConnectionPoint(ConnectionPoint.RIGHT, spaceField8)
        addConnectionPoint(ConnectionPoint.UP, spaceField6)
        addConnectionPoint(ConnectionPoint.DOWN, leftDownCorner)

        setPosition(offsetX, offsetY)
    }

}