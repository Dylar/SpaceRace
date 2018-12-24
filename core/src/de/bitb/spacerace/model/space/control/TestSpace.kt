package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.config.PLAYER_AMOUNT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.groups.CrossGroup
import de.bitb.spacerace.model.space.groups.TestGroup

class TestSpace(game: MainGame) : GameController(game) {
    override fun createSpace(game: MainGame) {
        super.createSpace(game)

//        val group1 = TestGroup(this)
//        val group2 = TestGroup(this, SCREEN_WIDTH.toFloat() * 1.2f, (SCREEN_HEIGHT / 2).toFloat())
//        val group3 = TestGroup(this, -SCREEN_WIDTH.toFloat() * 1.2f, -(SCREEN_HEIGHT / 2).toFloat())
//        val group4 = TestGroup(this, offsetY = SCREEN_HEIGHT * 1.2f)

        val offsetY = SCREEN_HEIGHT * 1.6f
        val offsetX = SCREEN_WIDTH.toFloat()

        val centerGroup = CrossGroup(this)
        val upGroup = TestGroup(this, offsetY = offsetY)
        val rightGroup = TestGroup(this, offsetX)
        val downGroup = TestGroup(this, offsetY = -offsetY)
        val leftGroup = TestGroup(this, -offsetX)


        fieldController.addFields(inputHandler, centerGroup, upGroup, rightGroup, downGroup, leftGroup)
        centerGroup.connect(ConnectionPoint.UP, upGroup)
        centerGroup.connect(ConnectionPoint.RIGHT, rightGroup)
        centerGroup.connect(ConnectionPoint.DOWN, downGroup)
        centerGroup.connect(ConnectionPoint.LEFT, leftGroup)

        val spaceField1 = centerGroup.fields[0]!!

        var player = Player(PlayerColor.TEAL)
        for (i in 1..PLAYER_AMOUNT) {
            playerController.players.add(player)
            fieldController.addShip(player, spaceField1)
            player = Player(player.playerData.playerColor.next())
        }

    }

}