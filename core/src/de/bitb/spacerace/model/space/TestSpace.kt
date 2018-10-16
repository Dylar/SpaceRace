package de.bitb.spacerace.model.space

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.base.GameColors
import de.bitb.spacerace.model.BaseSpace
import de.bitb.spacerace.model.SpaceField
import de.bitb.spacerace.model.enums.FieldType

class TestSpace : BaseSpace() {
    override fun createSpace() {
        val screenWidth = Gdx.graphics.width
        val screenHeight = Gdx.graphics.height

        val spaceField1 = SpaceField(fields.size, FieldType.GIFT)
        addField(spaceField1)
        val spaceField2 = SpaceField(fields.size, FieldType.MINE)
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


        addConnection(spaceField1, spaceField3)
        addConnection(spaceField2, spaceField3)
        addConnection(spaceField5, spaceField6)
        addConnection(spaceField5, spaceField4)
        addConnection(spaceField8, spaceField4)
        addConnection(spaceField2, spaceField4)
        addConnection(spaceField3, spaceField7)
        addConnection(spaceField8, spaceField7)
        addConnection(spaceField1, spaceField6)
        addConnection(spaceField7, spaceField6)
        addConnection(spaceField2, spaceField9)

        addShip(spaceField1, GameColors.GREEN)
        addShip(spaceField1, GameColors.RED)
        addShip(spaceField1, GameColors.YELLOW)

    }

}