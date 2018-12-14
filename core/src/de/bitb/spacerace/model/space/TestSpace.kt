package de.bitb.spacerace.model.space

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.model.enums.ConnectionPoint

class TestSpace : BaseSpace() {
    override fun createSpace() {

        val group1 = TestGroup(this)
        val group2 = TestGroup(this, SCREEN_WIDTH.toFloat() * 1.2f, (SCREEN_HEIGHT / 2).toFloat())
        val group3 = TestGroup(this, -SCREEN_WIDTH.toFloat() * 1.2f, -(SCREEN_HEIGHT / 2).toFloat())
        val group4 = TestGroup(this, offsetY = SCREEN_HEIGHT * 1.2f)
        addFields(group1, group2, group3, group4)
        group1.connect(ConnectionPoint.RIGHT, group2)
        group1.connect(ConnectionPoint.LEFT, group3)
        group1.connect(ConnectionPoint.UP, group4)

        val spaceField1 = group1.fields[0]!!
        addShip(spaceField1, PlayerColor.GREEN)
        addShip(spaceField1, PlayerColor.RED)
        addShip(spaceField1, PlayerColor.YELLOW)

    }

}