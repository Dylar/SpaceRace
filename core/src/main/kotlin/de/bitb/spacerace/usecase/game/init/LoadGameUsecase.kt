package de.bitb.spacerace.usecase.game.init

import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.PlayerColorDispenser
import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.map.NONE_FIELD_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.SelectMorePlayerException
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

class LoadGameUsecase @Inject constructor(
        private val playerController: PlayerController,
        private val playerColorDispenser: PlayerColorDispenser,
        private val playerDataSource: PlayerDataSource,
        private val mapDataSource: MapDataSource
) : ResultUseCase<LoadGameResult, LoadGameConfig> {

    override fun buildUseCaseSingle(params: LoadGameConfig): Single<LoadGameResult> =
            params.let { (map) ->
                checkPlayerSize(map.players)
//                        .andThen(mapDataSource.deleteSaveGame(map))
//                        .andThen(playerDataSource.deleteAll()) //TODO put player nt on map (except savegamesa bla bla) player color on multiple palyers ... change that
                        .andThen(initMap(map))
                        .flatMap {
                            it.saveData.name = Calendar.getInstance().time.toString()

                            mapDataSource.insertSaveGame(it.saveData)
                                    .andThen(Single.just(it))
                        }
                        .doAfterSuccess { pushCurrentPlayer(it.currentColor) }
            }

    private fun checkPlayerSize(players: List<PlayerData>): Completable =
            Completable.create { emitter ->
                if (players.size > 1) emitter.onComplete()
                else emitter.onError(SelectMorePlayerException())
            }

    private fun initMap(map: SaveData): Single<LoadGameResult> =
            Single.fromCallable {
                map.players.map { it.playerColor }.forEach { playerController.addPlayer(it) }

                map.goal.target = map.fields.find { it.fieldType == FieldType.GOAL } //TODO maybe another? TESTS?
                        ?: NONE_FIELD_DATA
                LoadGameResult(
                        currentColor = map.currentColor,
                        saveData = map)

            }

    private fun pushCurrentPlayer(currentColor: PlayerColor) =
            playerColorDispenser.publisher.onNext(currentColor)

}

data class LoadGameConfig(
        var saveData: SaveData
)

data class LoadGameResult(
        var currentColor: PlayerColor,
        var saveData: SaveData
)
