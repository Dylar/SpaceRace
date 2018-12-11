package de.bitb.spacerace.model.space

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.config.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType

class TestGroup(space: BaseSpace, offsetX: Float = 0f, offsetY: Float = 0f) : SpaceGroup(space, offsetX, offsetY) {

    val screenWidth = SCREEN_WIDTH
    val screenHeight = SCREEN_HEIGHT

    init {
        val spaceField1 = SpaceField(fields.size, FieldType.GIFT)
        addField(spaceField1)
        val spaceField2 = MineField(fields.size)
        addField(spaceField2, screenWidth - spaceField2.width)
        val spaceField3 = SpaceField(fields.size, FieldType.LOSE)
        addField(spaceField3, screenWidth / 2 - spaceField3.width / 2)
        val spaceField4 = SpaceField(fields.size, FieldType.AMBUSH)
        addField(spaceField4, screenWidth - spaceField4.width, screenHeight - spaceField4.height)
        val spaceField5 = SpaceField(fields.size, FieldType.UNKNOWN)
        addField(spaceField5, screenWidth / 2 - spaceField5.width / 2, screenHeight - spaceField5.height)
        val spaceField6 = SpaceField(fields.size, FieldType.LOSE)
        addField(spaceField6, posY = screenHeight - spaceField6.height)
        val spaceField7 = SpaceField(fields.size, FieldType.SHOP)
        addField(spaceField7, ((screenWidth / 3 - spaceField7.width * 2 / 3)), (screenHeight / 2 - spaceField7.height / 2))
        val spaceField8 = SpaceField(fields.size, FieldType.RANDOM)
        addField(spaceField8, ((screenWidth * 2 / 3 + spaceField8.width / 3)), (screenHeight / 2 - spaceField8.height / 2))

        val spaceField9 = SpaceField(fields.size, FieldType.WIN)
        addField(spaceField9, screenWidth.toFloat(), posY = -(screenHeight / 2 - spaceField9.height / 2))

        space.addConnection(spaceField1, spaceField3)
        space.addConnection(spaceField2, spaceField3)
        space.addConnection(spaceField5, spaceField6)
        space.addConnection(spaceField5, spaceField4)
        space.addConnection(spaceField8, spaceField4)
        space.addConnection(spaceField2, spaceField4)
        space.addConnection(spaceField3, spaceField7)
        space.addConnection(spaceField8, spaceField7)
        space.addConnection(spaceField1, spaceField6)
        space.addConnection(spaceField7, spaceField6)
        space.addConnection(spaceField2, spaceField9)

        addConnectionPoint(ConnectionPoint.UP, spaceField5)
        addConnectionPoint(ConnectionPoint.DOWN, spaceField3)
        addConnectionPoint(ConnectionPoint.LEFT, spaceField7)
        addConnectionPoint(ConnectionPoint.RIGHT, spaceField8)
        addConnectionPoint(ConnectionPoint.UP, spaceField6)
        addConnectionPoint(ConnectionPoint.DOWN, spaceField1)

        setPosition(offsetX, offsetY)
    }

    private fun addField(spaceField: SpaceField, posX: Float = spaceField.x, posY: Float = spaceField.y, connection: ConnectionPoint = ConnectionPoint.NONE) {
        spaceField.id = fields.size
        spaceField.group = this
        spaceField.setPosition(posX, posY)
        fields[spaceField.id] = spaceField
        if (connection != ConnectionPoint.NONE) {
            getConnection(connection).add(spaceField)
        }
        addActor(spaceField)
    }

}