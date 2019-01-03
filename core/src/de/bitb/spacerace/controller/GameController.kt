package de.bitb.spacerace.controller

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.maps.MapCollection
import de.bitb.spacerace.model.space.maps.SpaceMap

class GameController(game: MainGame) {
    val victories: MutableMap<PlayerColor, Int> = HashMap()
    val gamePlayer: MutableList<PlayerColor> = ArrayList()
    var spaceMap: MapCollection = MapCollection.RANDOM

    val inputHandler = InputHandler(game)
    val playerController = PlayerController()
    val fieldController = FieldController(playerController)

    init {
        PlayerColor.values().forEach { field -> victories[field] = 0 }
    }

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

