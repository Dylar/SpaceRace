package de.bitb.spacerace.usecase.game.init

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.PlayerColorDispenser
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.SpaceGroup
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Single
import javax.inject.Inject

class LoadGameUsecase @Inject constructor(
        private val graphicController: GraphicController,
        private val playerController: PlayerController,
        private val fieldController: FieldController,
        private val playerColorDispenser: PlayerColorDispenser,
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<List<PlayerData>, List<PlayerColor>> {

    override fun buildUseCaseSingle(params: List<PlayerColor>): Single<List<PlayerData>> =
            playerDataSource
                    .delete()
                    .andThen(playerDataSource.insertAllReturnAll(*params.map { PlayerData(playerColor = it) }.toTypedArray()))
                    .flatMap { initMap(it) }
                    .map { (startField, players) ->
                        graphicController.clearGraphics()

                        players.apply {
                            forEach { addPlayer(it, startField) }
                        }
                    }
                    .doAfterSuccess {
                        pushCurrentPlayer()
                    }

    private fun pushCurrentPlayer() {
        playerColorDispenser.publisher.onNext(playerController.currentColor)
    }


    private fun initMap(players: List<PlayerData>): Single<Pair<SpaceField, List<PlayerData>>> =
            fieldController.spaceMap
                    .createMap()
                    .let { addFields(it) }
                    .let { Single.just(it.startField to players) }
//                    .map { it.startField to players }


    //    private fun addFields(map: SpaceMap): Single<SpaceMap> =
//            Single.fromCallable{
    private fun addFields(map: SpaceMap): SpaceMap =
            map.also {
                fieldController.map = map
                fieldController.setRandomGoal()
                map.also {
                    it.groups.forEach { spaceGroup ->
                        spaceGroup.fields.entries.forEach { field ->
                            addField(field.value)
                        }
                    }
                }
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
}


    // TODO OOOLD

//    override fun buildUseCaseSingle(params: List<PlayerColor>): Single<List<PlayerData>> =
//            playerDataSource
//                    .insertAllReturnAll(*params.map { PlayerData(playerColor = it) }.toTypedArray())
//                    .flatMap { playerDataSource.getAll() }
//                    .flatMap { insertNewPlayer(it, params) }
//                    .map { players ->
//                        graphicController.clearGraphics()
//
//                        val map = initMap()
//
//                        val startField = map.startField
//                        players.forEach { addPlayer(it, startField) }
//                        players
//                    }
//                    .doAfterSuccess {
//                        pushCurrentPlayer()
//                    }
//
//    private fun insertNewPlayer(list: List<PlayerData>, params: List<PlayerColor>): Single<List<PlayerData>> {
//        return playerDataSource
//                .insertAllReturnAll(*params
//                        .map { color ->
//                            list.find { it.playerColor == color }
//                                    ?.let { it }
//                                    ?: PlayerData(playerColor = color)
//                        }.toTypedArray()
//                )
//    }
//
//    private fun pushCurrentPlayer() {
//        playerColorDispenser.publisher.onNext(playerController.currentColor)
//    }
//
//
//    private fun initMap(): SpaceMap {
//        return fieldController.spaceMap
//                .createMap()
//                .also {
//                    fieldController.map = it
//                    fieldController.setRandomGoal()
//                    addFields(*it.groups.toTypedArray())
//                }
//    }
//
//    private fun addFields(vararg spaceGroups: SpaceGroup) {
//        spaceGroups.forEach { spaceGroup ->
//            spaceGroup.fields.entries.forEach { field ->
//                addField(field.value)
//            }
//        }
//    }
//
//    private fun addField(spaceField: SpaceField) {
//        graphicController.addField(spaceField)
//        fieldController.addField(spaceField)
//    }
//
//    private fun addPlayer(playerData: PlayerData, startField: SpaceField) {
//        graphicController.addPlayer(playerData, startField)
//        playerController.addPlayer(playerData.playerColor)
////        player.playerImage.movingSpeed * playerData.index
//    }
//}