package de.bitb.spacerace.controller

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
    val gamePlayer: MutableList<PlayerColor> = mutableListOf()

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
        val map = fieldController.initMap(this)

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

}

