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
) : ResultUseCase<LoadGameInfo, List<PlayerColor>> {

    override fun buildUseCaseSingle(params: List<PlayerColor>): Single<LoadGameInfo> =
            Completable.create {
                if (params.size > 1) it.onComplete()
                else it.onError(SelectMorePlayerException())
            }.andThen(playerDataSource.deleteAll()
            ).andThen(playerDataSource.insertAllReturnAll(*params.map { PlayerData(playerColor = it) }.toTypedArray())
            ).flatMap {
                initMap(it)
            }.doAfterSuccess {
                pushCurrentPlayer(it.currentColor)
            }

    private fun initMap(players: List<PlayerData>): Single<LoadGameInfo> =
            Single.fromCallable {
                graphicController.clearGraphics()
                val map = fieldController.spaceMap.createMap()

                fieldController.map = map
                fieldController.setRandomGoal()
                map.groups.forEach { spaceGroup ->
                    spaceGroup.fields.entries.forEach { field ->
                        addField(field.value)
                    }
                }
                players.forEach { addPlayer(it, map.startField) }

                LoadGameInfo(
                        currentColor = players.first().playerColor,
                        players = players,
                        goal = fieldController.currentGoal!!)
            }

    private fun addField(spaceField: SpaceField) {
        graphicController.addField(spaceField)
        fieldController.addField(spaceField)
    }

    private fun addPlayer(playerData: PlayerData, startField: SpaceField) {
        graphicController.addPlayer(playerData, startField)
        playerController.addPlayer(playerData.playerColor)
//        player.playerImage.movingSpeed * playerData.index
    }

    private fun pushCurrentPlayer(currentColor: PlayerColor) =
            playerColorDispenser.publisher.onNext(currentColor)

}