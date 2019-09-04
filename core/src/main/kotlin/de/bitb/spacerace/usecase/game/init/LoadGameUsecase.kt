package de.bitb.spacerace.usecase.game.init

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.LoadGameInfo
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.PlayerColorDispenser
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.SelectMorePlayerException
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoadGameUsecase @Inject constructor(
        private val graphicController: GraphicController,
        private val playerController: PlayerController,
        private val fieldController: FieldController,
        private val playerColorDispenser: PlayerColorDispenser,
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<LoadGameInfo, LoadGameConfig> {

    override fun buildUseCaseSingle(params: LoadGameConfig): Single<LoadGameInfo> =
            params.let { (players, map) ->
                checkPlayerSize(players)
                        .andThen(playerDataSource.deleteAll()
                        ).andThen(playerDataSource.insertAllReturnAll(*players.map { PlayerData(playerColor = it) }.toTypedArray())
                        ).flatMap {
                            initMap(it, map)
                        }.doAfterSuccess { pushCurrentPlayer(it.currentColor) }
            }


    private fun checkPlayerSize(players: List<PlayerColor>): Completable =
            Completable.create { emitter ->
                if (players.size > 1) emitter.onComplete()
                else emitter.onError(SelectMorePlayerException())
            }

    private fun initMap(players: List<PlayerData>, mapToLoad: SpaceMap): Single<LoadGameInfo> =
            Single.fromCallable {
                //TODO clean from graphics
                graphicController.clearGraphics()
                mapToLoad.let { map ->
                    map.groups.forEach { spaceGroup ->
                        spaceGroup.fields.entries.forEach { field ->
                            addField(field.value)
                        }
                    }
                    players.forEach { addPlayer(it, map.startField) }

                    map.firstGoal = fieldController.setRandomGoal().second

                    LoadGameInfo(
                            currentColor = players.first().playerColor,
                            players = players,
                            map = map)
                }

            }

    private fun addField(spaceField: SpaceField) {
        graphicController.addField(spaceField)
        fieldController.addField(spaceField)
    }

    private fun addPlayer(playerData: PlayerData, startField: SpaceField) {
        graphicController.addPlayer(playerData.playerColor, startField)
        playerController.addPlayer(playerData.playerColor)
//        player.playerImage.movingSpeed * playerData.index
    }

    private fun pushCurrentPlayer(currentColor: PlayerColor) =
            playerColorDispenser.publisher.onNext(currentColor)

}

data class LoadGameConfig(
        var players: List<PlayerColor>,
        var mapToLoad: SpaceMap
)
