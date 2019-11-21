package de.bitb.spacerace.usecase.game.init

import de.bitb.spacerace.config.DEBUG_PLAYER_ITEMS
import de.bitb.spacerace.config.DEBUG_PLAYER_ITEMS_COUNT
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.core.exceptions.SelectMorePlayerException
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.map.NONE_FIELD_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.database.savegame.SaveDataSource
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.dispender.PlayerColorDispenser
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoadNewGameUsecase @Inject constructor(
        private val loadGameUsecase: LoadGameUsecase,
        private val mapDataSource: MapDataSource
) : ResultUseCase<LoadGameResult, LoadGameConfig> {

    override fun buildUseCaseSingle(params: LoadGameConfig): Single<LoadGameResult> =
            params.let { (players, mapName) ->
                checkPlayerSize(players)
                        .andThen(initPlayers(players))
                        .flatMap { initMap(it, mapName) }
                        .doOnSuccess { initDebugItem(it) }
                        .flatMap { loadGameUsecase.buildUseCaseSingle(it) }
            }

    private fun initDebugItem(saveData: SaveData) {
        //DEBUG items
        if (DEBUG_PLAYER_ITEMS.isNotEmpty()) {
            initPlayerItems(saveData, DEBUG_PLAYER_ITEMS)
        }
    }

    private fun initPlayers(players: List<PlayerColor>): Single<SaveData> =
            Single.fromCallable {
                SaveData().also { saveGame ->
                    players.forEach { color ->
                        saveGame.players.add(PlayerData(playerColor = color))
                    }
                    saveGame.currentColor = players.first()
                }
            }

    private fun checkPlayerSize(players: List<PlayerColor>): Completable =
            Completable.create { emitter ->
                if (players.size > 1) emitter.onComplete()
                else emitter.onError(SelectMorePlayerException())
            }

    private fun initMap(saveData: SaveData, mapName: String): Single<SaveData> =
            Single.fromCallable {
                val map = mapDataSource.getDBMaps(mapName).first()
                val connections = mutableMapOf<PositionData, FieldData>()
                //add fields
                map.fields.forEach { config ->
                    val fieldData = FieldData(
                            fieldType = config.fieldType,
                            gamePosition = config.gamePosition)
                    saveData.fields.add(fieldData)
                }

                //add connections
                map.fields.forEach { config ->
                    val field = saveData.fields.find { it.gamePosition.isPosition(config.gamePosition) }!!
                    config.connections.forEach { conPos ->
                        val con = saveData.fields.find { it.gamePosition.isPosition(conPos) }!!
                        field.connections.add(con)
                    }
                }

                //set goal
                saveData.goal.target = saveData.fields.find { it.fieldType == FieldType.GOAL }
                        ?: NONE_FIELD_DATA

                //set player startfield
                val startField = saveData.fields.find { map.startPosition.isPosition(it.gamePosition) }
                saveData.players.forEach { it.positionField.target = startField }

                saveData
            }

}

class LoadGameUsecase
@Inject constructor(
        private val playerController: PlayerController,
        private val saveDataSource: SaveDataSource,
        private val playerColorDispenser: PlayerColorDispenser
) : ResultUseCase<LoadGameResult, SaveData> {

    override fun buildUseCaseSingle(params: SaveData): Single<LoadGameResult> =
            loadGame(params)
                    .doOnSuccess { initPlayer(it) }
                    .map { LoadGameResult(it) }
                    .doAfterSuccess { pushCurrentPlayer(it.saveData.currentColor) }

    private fun loadGame(saveData: SaveData): Single<SaveData> =
            saveDataSource.getRXAllGames()
                    .map { it.onEach { save -> save.loaded = false } }
                    .flatMapCompletable { saveDataSource.insertRXSaveData(*it.toTypedArray()) }
                    .andThen(Single.fromCallable { saveData.also { it.loaded = true } })
                    .flatMap { saveDataSource.insertAndReturnRXSaveData(it).map { it.first() } }

    private fun initPlayer(saveData: SaveData) {
        saveData.players
                .map { it.playerColor }
                .forEach { playerController.addPlayer(it) }
    }

    private fun pushCurrentPlayer(currentColor: PlayerColor) =
            playerColorDispenser.publisher.onNext(currentColor)
}

data class LoadGameConfig(
        val players: List<PlayerColor>,
        val mapName: String
)

data class LoadGameResult(
        var saveData: SaveData
)


private fun initPlayerItems(
        saveData: SaveData,
        items: List<ItemInfo>
) {
    saveData.players //TODO init random on live
            .onEach { player ->
                items.map { itemInfo ->
                    repeat(DEBUG_PLAYER_ITEMS_COUNT) {
                        val item = ItemData.createItem(player, itemInfo)
                        player.storageItems.add(item)
                    }
                }
            }
}