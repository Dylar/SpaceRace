package de.bitb.spacerace.usecase.game.init

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.PlayerColorDispenser
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.database.map.NONE_FIELD_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.SelectMorePlayerException
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoadGameUsecase @Inject constructor(
        private val playerController: PlayerController,
        private val fieldController: FieldController,
        private val playerColorDispenser: PlayerColorDispenser,
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<LoadGameInfo, LoadGameConfig> {

    override fun buildUseCaseSingle(params: LoadGameConfig): Single<LoadGameInfo> =
            params.let { (map) ->
                checkPlayerSize(map.players)
                        .andThen(initMap(map))
                        .flatMap { result ->
                            playerDataSource.deleteAll()
                            playerDataSource.insertAllReturnAll(*result.map.players.toTypedArray())
                                    .map { playerData ->
                                        result//.also { result.map.players = playerData }
                                    }
                        }
            }.doAfterSuccess { pushCurrentPlayer(it.currentColor) }

    private fun checkPlayerSize(players: List<PlayerData>): Completable =
            Completable.create { emitter ->
                if (players.size > 1) emitter.onComplete()
                else emitter.onError(SelectMorePlayerException())
            }

    private fun initMap(map: MapData): Single<LoadGameInfo> =
            Single.fromCallable {
                map.fields.forEach { fieldController.addField(it) }
                map.players.map { it.playerColor }.forEach { playerController.addPlayer(it) }

                val goalPosition = fieldController.setRandomGoalPosition().second
                map.goal.target = map.fields.find { it.gamePosition.isPosition(goalPosition) }
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
