package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.SELECTED_MAP
import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.objecthandling.NONE_POSITION
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.model.space.maps.createMap
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.usecase.game.init.LoadGameConfig
import de.bitb.spacerace.usecase.game.init.LoadGameUsecase
import javax.inject.Inject

class LoadGameCommand() : BaseCommand() {

    @Inject
    protected lateinit var loadGameUsecase: LoadGameUsecase

    @Inject
    protected lateinit var game: MainGame

    @Inject
    protected lateinit var graphicController: GraphicController

    init {
        MainGame.appComponent.inject(this)
    }

    override fun canExecute(): Boolean {
        return SELECTED_PLAYER.size > 1
    }

    override fun execute() {
        val mapData = MapData()
        graphicController.clearGraphics()
//        val map = initMap(SELECTED_PLAYER, SELECTED_MAP.name)
        game.changeScreen(GameScreen(game, game.screen as BaseScreen))

        val config = LoadGameConfig(
                players = SELECTED_PLAYER,
                mapName = SELECTED_MAP.name)

        loadGameUsecase.getResult(
                params = config,
                onSuccess = { info ->
                    graphicController.setGoal(NONE_POSITION to info.map.firstGoal)
                    game.startGameDELETE_ME()
                })
    }
//TODO RETURN MAPDATA
    private fun initMap(players: List<PlayerColor>, mapName: String): SpaceMap =
                mapName.createMap().also { map ->
                    map.groups.forEach { spaceGroup ->
                        spaceGroup.fields.entries.forEach { field ->
                            addField(field.value)
                        }
                    }
                    graphicController.connectionGraphics.addAll(map.connections)
                    players.forEach {
                        addPlayer(it, map.startField)
                    }
                }


    private fun addField(spaceField: SpaceField) {
        graphicController.addField(spaceField)
    }

    private fun addPlayer(playerColor: PlayerColor, startField: SpaceField) {
        graphicController.addPlayer(playerColor, startField)
    }
}