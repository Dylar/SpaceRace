package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.config.PLAYER_AMOUNT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.groups.TestGroup

class TestSpace() : GameController() {
    override fun createSpace(inputHandler: InputHandler) {
        fieldController.connections = ConnectionList(inputHandler.gameController)

        val group1 = TestGroup(this)
        val group2 = TestGroup(this, SCREEN_WIDTH.toFloat() * 1.2f, (SCREEN_HEIGHT / 2).toFloat())
        val group3 = TestGroup(this, -SCREEN_WIDTH.toFloat() * 1.2f, -(SCREEN_HEIGHT / 2).toFloat())
        val group4 = TestGroup(this, offsetY = SCREEN_HEIGHT * 1.2f)
        val group5 = TestGroup(this, offsetY = -SCREEN_HEIGHT * 1.2f)
        fieldController.addFields(inputHandler, group1, group2, group3, group4, group5)
        group1.connect(ConnectionPoint.RIGHT, group2)
        group1.connect(ConnectionPoint.LEFT, group3)
        group1.connect(ConnectionPoint.UP, group4)
        group1.connect(ConnectionPoint.DOWN, group5)
        group3.connect(ConnectionPoint.RIGHT, group5)

        val spaceField1 = group1.fields[0]!!

        var player = Player(PlayerColor.TEAL)
        for (i in 1..PLAYER_AMOUNT) {
            playerController.players.add(player)
            fieldController.addShip(player, spaceField1)
            player = Player(player.playerData.playerColor.next())
        }

    }

}