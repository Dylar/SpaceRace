package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.database.player.PlayerColorDispender
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.events.commands.phases.OpenEndRoundMenuCommand
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.fields.MineField
import de.bitb.spacerace.usecase.UseCaseWithoutParams
import io.reactivex.Observable
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class ObserveRoundUsecase @Inject constructor(
        private val fieldController: FieldController,
        private val playerDataSource: PlayerDataSource
) : UseCaseWithoutParams<Boolean>() {

    override fun buildUseCaseObservable(): Observable<Boolean> =
            playerDataSource.observeAllObserver()
                    .map(::filterPlayer)
                    .map(::endRound)
                    .flatMap(::updatePlayer)

    private fun filterPlayer(player: List<PlayerData>) =
            player.filter { it.phase.isEndTurn() }.toMutableList()
                    .apply {
                        if (size != player.size) {
                            clear()
                        }
                    }

    private fun endRound(player: List<PlayerData>) =
            player.apply {
                if (isNotEmpty()) {
                    fieldController.moveMovables()
                    fieldController.fieldsMap[FieldType.MINE]
                            ?.map { it as MineField }
                            ?.forEach { mine ->
                                player.find { mine.owner == it.playerColor }?.addRandomWin()
                            }
                    forEach { it.nextRound() }
                }
            }

    private fun updatePlayer(player: List<PlayerData>) =
            playerDataSource
                    .insertAll(*player.toTypedArray())
                    .map { player.isNotEmpty() }
                    .doOnSuccess { roundEnd ->
                        if (roundEnd) {
                            EventBus.getDefault().post(OpenEndRoundMenuCommand())
                        }
                    }.toObservable()
}