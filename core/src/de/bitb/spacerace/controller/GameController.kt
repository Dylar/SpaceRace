package de.bitb.spacerace.controller

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.Logger
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.ui.screens.game.GameStage
import javax.inject.Inject

class GameController() : DefaultFunction by object : DefaultFunction {} {
    val victories: MutableMap<PlayerColor, Int> = HashMap()
    val gamePlayer: MutableList<PlayerColor> = mutableListOf()
    var currentGoal: SpaceField = SpaceField.NONE

    @Inject
    lateinit var inputHandler: InputHandler

    @Inject
    lateinit var playerController: PlayerController

    @Inject
    lateinit var fieldController: FieldController

    init {
        MainGame.appComponent.inject(this)
    }

    fun initGame(game: MainGame, playerDatas: List<PlayerData>) {
        PlayerColor.values().forEach { field -> victories[field] = 0 }
        val map = fieldController.initMap(this)
        setRandomGoal()

        playerController.clearPlayer()
        val startField = map.startField
        for (playerData in playerDatas.withIndex()) {
            Logger.println("NEXT: loadPlayerUsecase: ${playerData.value}")
            addPlayer(playerData, startField)
        }
        val gameStage = (game.screen as BaseScreen).gameStage as GameStage
        gameStage.addEntitiesToMap()
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
        currentGoal = fieldController.map.getRandomGoal()
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

