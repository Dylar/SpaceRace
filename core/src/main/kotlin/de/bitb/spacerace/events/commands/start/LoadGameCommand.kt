package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.SELECTED_MAP
import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.space.fields.ConnectionGraphic
import de.bitb.spacerace.model.space.fields.FieldGraphic
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.usecase.game.init.LoadGameConfig
import de.bitb.spacerace.usecase.game.init.LoadGameUsecase
import javax.inject.Inject

class LoadGameCommand(var saveData: SaveData? = null) : BaseCommand() {

    @Inject
    protected lateinit var loadGameUsecase: LoadGameUsecase

    @Inject
    protected lateinit var game: MainGame

    @Inject
    protected lateinit var graphicController: GraphicController

    init {
        MainGame.appComponent.inject(this)
        saveData = saveData ?: run {
            val map = game.initDefaultMap(SELECTED_MAP.createMap())
            game.createNewSaveGame(SELECTED_PLAYER, map)
        }
    }

    override fun canExecute(): Boolean {
        return true
    }

    override fun execute() {
        game.changeScreen(GameScreen(game, game.screen as BaseScreen))

        val config = LoadGameConfig(saveData = saveData!!)
        loadGameUsecase.getResult(
                params = config,
                onSuccess = { info ->
                    graphicController.clearGraphics()

                    val fields = info.saveData.fields
                    addGraphicFields(fields)
                    addGraphicConnections(fields)
                    addGraphicPlayers(info.saveData.players)

                    graphicController.setGoal(currentGoal = info.saveData.goal.target.gamePosition)
                    game.addEntities()
                    game.initGameObserver()
                })
    }

    private fun addGraphicPlayers(players: List<PlayerData>) {
        players.forEach {
            graphicController.addPlayer(it.playerColor, graphicController.getFieldGraphic(it.gamePosition))
        }
    }

    private fun addGraphicConnections(fields: List<FieldData>) {
        val connections = mutableListOf<ConnectionGraphic>()
        fields.forEach { thisField ->
            thisField.connections.forEach { thatField ->
                if (connections.none { it.isConnection(thisField.gamePosition, thatField.gamePosition) }) {
                    val connection = ConnectionGraphic(
                            graphicController.getFieldGraphic(thisField.gamePosition),
                            graphicController.getFieldGraphic(thatField.gamePosition))
                    connections.add(connection)
                }
            }
        }
        graphicController.connectionGraphics.addAll(connections)
    }

    private fun addGraphicFields(fields: List<FieldData>) {
        fields.forEach { fieldData ->
            val fieldGraphic = FieldGraphic
                    .createField(fieldData.fieldType)
                    .apply { setPosition(fieldData.gamePosition) }
            graphicController.addField(fieldGraphic)
        }
    }

}