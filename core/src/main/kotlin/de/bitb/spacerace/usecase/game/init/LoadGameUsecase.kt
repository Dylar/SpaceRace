package de.bitb.spacerace.usecase.game.init

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.PlayerColorDispenser
import de.bitb.spacerace.database.map.MapData
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
import javax.inject.Inject

class LoadGameUsecase @Inject constructor(
        private val playerController: PlayerController,
        private val fieldController: FieldController,
        private val playerColorDispenser: PlayerColorDispenser,
        private val playerDataSource: PlayerDataSource,
        private val mapDataSource: MapDataSource
) : ResultUseCase<LoadGameInfo, LoadGameConfig> {

    override fun buildUseCaseSingle(params: LoadGameConfig): Single<LoadGameInfo> =
            params.let { (map) ->
                checkPlayerSize(map.players)
                        .andThen(mapDataSource.deleteMap())
                        .andThen(playerDataSource.deleteAll()) //TODO put player nt on map (except savegamesa bla bla) player color on multiple palyers ... change that
                        .andThen(initMap(map))
                        .flatMap {
                            mapDataSource.insertMap(it.map)
                                    .andThen(Single.just(it))
                        }
                        .doAfterSuccess { pushCurrentPlayer(it.currentColor) }
            }

    private fun checkPlayerSize(players: List<PlayerData>): Completable =
            Completable.create { emitter ->
                if (players.size > 1) emitter.onComplete()
                else emitter.onError(SelectMorePlayerException())
            }

    private fun initMap(map: MapData): Single<LoadGameInfo> =
            Single.fromCallable {
                map.fields.forEach { fieldController.addField(it) }
                map.players.map { it.playerColor }.forEach { playerController.addPlayer(it) }

                map.goal.target = map.fields.find { it.fieldType == FieldType.GOAL } //TODO maybe another?
                        ?: NONE_FIELD_DATA
                LoadGameInfo(
                        currentColor = map.players.first().playerColor,
                        map = map)

            }

    private fun pushCurrentPlayer(currentColor: PlayerColor) =
            playerColorDispenser.publisher.onNext(currentColor)

}

data class LoadGameConfig(
        var map: MapData
)

data class LoadGameInfo(
        var currentColor: PlayerColor,
        var map: MapData
)
