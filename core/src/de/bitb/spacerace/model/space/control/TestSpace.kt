package de.bitb.spacerace.model.space.control

import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.groups.CircleGroup
import de.bitb.spacerace.model.space.groups.CrossGroup

class TestSpace(game: MainGame) : GameController(game) {
    override fun createSpace(game: MainGame) {
        super.createSpace(game)

        val offsetY = SCREEN_HEIGHT * 1.6f
        val offsetX = SCREEN_WIDTH.toFloat()

        val centerGroup = CrossGroup(this)
        val upGroup = CircleGroup(this, offsetY = offsetY)
        val rightGroup = CircleGroup(this, offsetX)
        val downGroup = CircleGroup(this, offsetY = -offsetY)
        val leftGroup = CircleGroup(this, -offsetX)


        fieldController.addFields(inputHandler, centerGroup, upGroup, rightGroup, downGroup, leftGroup)
        centerGroup.connect(ConnectionPoint.TOP, upGroup)
        centerGroup.connect(ConnectionPoint.RIGHT, rightGroup)
        centerGroup.connect(ConnectionPoint.BOTTOM, downGroup)
        centerGroup.connect(ConnectionPoint.LEFT, leftGroup)

        val spaceField1 = centerGroup.fields[0]!!

        for (playerColor in gamePlayer) {
            val player = Player(playerColor)
            playerController.players.add(player)
            fieldController.addShip(player, spaceField1)
        }

//        var player = Player(PlayerColor.RED)
//        for (i in 1..PLAYER_AMOUNT) {
//            playerController.players.add(player)
//            fieldController.addShip(player, spaceField1)
//            player = Player(player.playerData.playerColor.next())
//        }

    }

}