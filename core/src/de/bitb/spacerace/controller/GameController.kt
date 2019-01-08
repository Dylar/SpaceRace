package de.bitb.spacerace.controller

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.maps.MapCollection
import de.bitb.spacerace.model.space.maps.SpaceMap

class GameController(game: MainGame) {
    val inputHandler = InputHandler(game)
    val playerController = PlayerController()
    val fieldController = FieldController(playerController)

    val victories: MutableMap<PlayerColor, Int> = HashMap()
    val gamePlayer: MutableList<PlayerColor> = ArrayList()
    var spaceMap: MapCollection = MapCollection.RANDOM
    var currentGoal: SpaceField = SpaceField.NONE
    lateinit var map: SpaceMap

    init {
        PlayerColor.values().forEach { field -> victories[field] = 0 }
    }

    fun initGame(map: SpaceMap) {
        this.map = map
        setRandomGoal()
        fieldController.initMap(this, map)

        val startField = map.startField
        for (playerColor in gamePlayer) {
            val player = Player(playerColor)
            playerController.players.add(player)
            playerController.playerMap[playerColor] = player
            fieldController.addShip(player, startField)
        }
    }

    fun setRandomGoal() {
        if (currentGoal != SpaceField.NONE) {
            currentGoal.setBlinkColor(null)
        }
        currentGoal = map.getRandomGoal()
        currentGoal.setBlinkColor(Color(currentGoal.fieldType.color))
    }

    fun getWinner(): PlayerColor {
        var winner: PlayerColor = PlayerColor.NONE
        victories.forEach { entrySet ->
            if (entrySet.value == WIN_AMOUNT) {
                winner = entrySet.key
                return@forEach
            }
        }
        return winner
    }

}

