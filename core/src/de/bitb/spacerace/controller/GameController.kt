package de.bitb.spacerace.controller

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.maps.MapCollection
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.usecase.LoadPlayerUsecase
import javax.inject.Inject

class GameController() : DefaultFunction by object : DefaultFunction {} {
    val victories: MutableMap<PlayerColor, Int> = HashMap()
    val gamePlayer: MutableList<PlayerColor> = ArrayList()
    var spaceMap: MapCollection = MapCollection.RANDOM
    var currentGoal: SpaceField = SpaceField.NONE

    @Inject
    lateinit var playerController: PlayerController

    @Inject
    lateinit var inputHandler: InputHandler

    @Inject
    lateinit var fieldController: FieldController

    @Inject
    protected lateinit var loadPlayerUsercase: LoadPlayerUsecase

    lateinit var map: SpaceMap

    init {
        MainGame.appComponent.inject(this)
        PlayerColor.values().forEach { field -> victories[field] = 0 }
    }

    fun initGame(map: SpaceMap) {
        this.map = map
        setRandomGoal()
        fieldController.initMap(this, map)

        val startField = map.startField
        //TODO make load game
        loadPlayerUsercase(
                gamePlayer.map { PlayerData(playerColor = it) },
                onNext = {
                    for (playerData in it.withIndex()) {
                        Logger.println("NEXT: loadPlayerUsercase: ${playerData.value}")
                        addPlayer(playerData, startField)
                    }
                },
                onError = {
                    Logger.println("NEXT ERROR: loadPlayerUsercase")
                    it.printStackTrace()
                })
    }

    private fun addPlayer(playerData: IndexedValue<PlayerData>, startField: SpaceField) {
        val player = Player(playerData.value)

        playerController.players.add(player)
        playerController.playerMap[playerData.value.playerColor] = player

        player.playerImage.movingSpeed * playerData.index
        fieldController.addShip(player, startField)
    }

    fun setRandomGoal() {
        currentGoal.fieldImage.setBlinkColor(null)
        currentGoal = map.getRandomGoal()
        currentGoal.fieldImage.setBlinkColor(Color(currentGoal.fieldType.color))
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

