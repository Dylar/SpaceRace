package de.bitb.spacerace.usecase.game.init

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.PlayerColorDispender
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.events.commands.player.MoveCommand
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.SpaceGroup
import de.bitb.spacerace.model.space.maps.SpaceMap
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class LoadGameUsecase @Inject constructor(
        private val graphicController: GraphicController,
        private val playerController: PlayerController,
        private val fieldController: FieldController,
        private val playerColorDispender: PlayerColorDispender,
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<List<PlayerData>, List<PlayerColor>> {

    override fun buildUseCaseSingle(params: List<PlayerColor>): Single<List<PlayerData>> =
            playerDataSource
                    .insertAllReturnAll(*params.map { PlayerData(playerColor = it) }.toTypedArray())
                    .flatMap { playerDataSource.getAll() }
                    .flatMap { insertNewPlayer(it, params) }
                    .map { players ->

                        val map = initMap()

                        graphicController.clearPlayer()
                        val startField = map.startField
                        players.forEach {
                            addPlayer(it, startField)
                        }
                        players
                    }
                    .doAfterSuccess {
                        pushCurrentPlayer(it)
                    }

    private fun insertNewPlayer(list: List<PlayerData>, params: List<PlayerColor>): Single<List<PlayerData>> {
        return playerDataSource
                .insertAllReturnAll(*params
                        .map { color ->
                            list.find { it.playerColor == color }
                                    ?.let { it }
                                    ?: PlayerData(playerColor = color)
                        }.toTypedArray()
                )
    }

    private fun pushCurrentPlayer(list: List<PlayerData>) {
        if (list.isNotEmpty()) {
            graphicController.currentPlayer.playerColor
                    .let {
                        when (it) {
                            PlayerColor.NONE -> list.first().playerColor
                            else -> it
                        }
                    }.also {
                        playerColorDispender.publisher.onNext(it)
                    }

        }
    }


    private fun initMap(): SpaceMap {
        fieldController.clearField()
        return fieldController.spaceMap
                .createMap()
                .also {
                    fieldController.map = it
                    fieldController.setRandomGoal()
                    addFields(*it.groups.toTypedArray())
                }
    }

    private fun addFields(vararg spaceGroups: SpaceGroup) {
        for (spaceGroup in spaceGroups) {
            for (field in spaceGroup.fields.entries.withIndex()) {
                addField(field.value.value)
            }
        }
    }

    private fun addField(spaceField: SpaceField) {
        spaceField.getGameImage().addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                EventBus.getDefault().post(MoveCommand(spaceField, playerController.currentPlayerData))
                return true
            }
        })
        fieldController.fields.add(spaceField)
        fieldController.addFieldMap(spaceField)
    }

    private fun addPlayer(playerData: PlayerData, startField: SpaceField) {
        graphicController.addPlayer(playerData, startField)
//        player.playerImage.movingSpeed * playerData.index
    }
}
