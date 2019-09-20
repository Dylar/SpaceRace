package de.bitb.spacerace.usecase.game.init

import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.PlayerColorDispenser
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.database.map.NONE_FIELD_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.database.savegame.SaveDataSource
import de.bitb.spacerace.exceptions.SelectMorePlayerException
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoadNewGameUsecase @Inject constructor(
        private val loadGameUsecase: LoadGameUsecase,
        private val playerController: PlayerController,
        private val playerColorDispenser: PlayerColorDispenser
) : ResultUseCase<LoadGameResult, LoadGameConfig> {

    override fun buildUseCaseSingle(params: LoadGameConfig): Single<LoadGameResult> =
            params.let { (players, map) ->
                checkPlayerSize(players)
                        .andThen(initPlayers(players))
                        .flatMap { initMap(it, map) }
                        .flatMap { loadGameUsecase.buildUseCaseSingle(it) }
                        .doAfterSuccess { pushCurrentPlayer(it.saveData.currentColor) }
            }

    private fun initPlayers(players: List<PlayerColor>): Single<SaveData> =
            Single.fromCallable {
                SaveData().also { saveGame ->
                    players.forEach { color ->
                        playerController.addPlayer(color)
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

    private fun initMap(saveData: SaveData, map: MapData): Single<SaveData> =
            Single.fromCallable {
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
                saveData.goal.target = saveData.fields.find { it.fieldType == FieldType.GOAL } //TODO maybe another? TESTS?
                        ?: NONE_FIELD_DATA

                //set player startfield
                val startField = saveData.fields.find { map.startPosition.isPosition(it.gamePosition) }
                saveData.players.forEach { it.positionField.target = startField }

                saveData
            }

    private fun pushCurrentPlayer(currentColor: PlayerColor) =
            playerColorDispenser.publisher.onNext(currentColor)

}

class LoadGameUsecase
@Inject constructor(
        private val saveDataSource: SaveDataSource
) : ResultUseCase<LoadGameResult, SaveData> {

    override fun buildUseCaseSingle(params: SaveData): Single<LoadGameResult> =
            saveDataSource.insertAndReturnSaveData(params.apply { loaded = true })
                    .flatMap { Single.fromCallable { LoadGameResult(it) } }
}

data class LoadGameConfig(
        val players: List<PlayerColor>,
        val mapData: MapData
)

data class LoadGameResult(
        var saveData: SaveData

)
