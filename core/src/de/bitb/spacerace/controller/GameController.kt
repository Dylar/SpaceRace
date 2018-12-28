package de.bitb.spacerace.controller

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.maps.SpaceMaps
import de.bitb.spacerace.model.space.maps.SpaceMap

class GameController(game: MainGame) {
    val gamePlayer: MutableList<PlayerColor> = ArrayList()
    var spaceMap: SpaceMaps = SpaceMaps.RANDOM

    val inputHandler = InputHandler(game)
    val playerController = PlayerController()
    val fieldController = FieldController(playerController)

    fun initGame(map: SpaceMap) {
        fieldController.initMap(inputHandler, map)

        val startField = map.startField
        for (playerColor in gamePlayer) {
            val player = Player(playerColor)
            playerController.players.add(player)
            playerController.playerMap[playerColor] = player
            fieldController.addShip(player, startField)
        }
    }

}

