package de.bitb.spacerace.events.commands.start

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.config.SELECTED_MAP
import de.bitb.spacerace.config.SELECTED_PLAYER
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.events.commands.CommandPool.getCommand
import de.bitb.spacerace.model.space.fields.ConnectionGraphic
import de.bitb.spacerace.model.space.fields.FieldGraphic
import de.bitb.spacerace.model.space.maps.initDefaultMap
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.usecase.game.init.LoadGameConfig
import de.bitb.spacerace.usecase.game.init.LoadGameResult
import de.bitb.spacerace.usecase.game.init.LoadGameUsecase
import de.bitb.spacerace.usecase.game.init.LoadNewGameUsecase
import javax.inject.Inject

class LoadGameCommand : BaseCommand() {

    companion object {
        fun get(saveData: SaveData? = null) =
                getCommand(LoadGameCommand::class)
                        .also { it.saveData = saveData }
    }

    @Inject
    protected lateinit var loadNewGameUsecase: LoadNewGameUsecase

    @Inject
    protected lateinit var loadGameUsecase: LoadGameUsecase

    @Inject
    protected lateinit var gameController: GameController

    @Inject
    protected lateinit var game: MainGame

    @Inject
    protected lateinit var graphicController: GraphicController

    private var saveData: SaveData? = null

    init {
        MainGame.appComponent.inject(this)
    }

    override fun execute() {
        saveData?.let { saveGame ->
            loadGameUsecase.getResult(
                    params = saveGame,
                    onSuccess = ::onSuccess,
                    onError = ::onError)
        } ?: kotlin.run {
            val map = SELECTED_MAP.createMap().initDefaultMap() //TODO delete or save somewhere
            val config = LoadGameConfig(SELECTED_PLAYER, map)
            loadNewGameUsecase.getResult(
                    params = config,
                    onSuccess = ::onSuccess,
                    onError = ::onError)
        }
    }

    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        reset()
    }

    private fun onSuccess(result: LoadGameResult) {
        setGraphics(result.saveData)
        reset()
    }

    private fun setGraphics(saveData: SaveData) {
        val screen = GameScreen(game, game.screen as BaseScreen)
        val fields = saveData.fields
        addGraphicFields(fields)
        addGraphicConnections(fields)
        addGraphicPlayers(saveData.players)
        graphicController.setGoal(currentGoal = saveData.goal.target.gamePosition)

        gameController.initGameObserver()
        game.changeScreen(screen)
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