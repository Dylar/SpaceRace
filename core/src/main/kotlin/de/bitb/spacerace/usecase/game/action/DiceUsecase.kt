package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.ExecuteUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject
import kotlin.math.absoluteValue

class DiceUsecase @Inject constructor(
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val playerDataSource: PlayerDataSource
) : ExecuteUseCase<Pair<PlayerColor, Int>> {

    override fun buildUseCaseCompletable(params: Pair<PlayerColor, Int>) =
            params.let { (playerColor, maxResult) ->
                checkCurrentPlayerUsecase.buildUseCaseSingle(playerColor)
                        .flatMapCompletable { dice(it, maxResult) }
            }

    private fun dice(playerData: PlayerData, maxResult: Int): Completable =
            if (canExecute(playerData)) {
                val result =
                        if (maxResult > 0) {
                            (Math.random() * maxResult).toInt() + 1
                        } else maxResult.absoluteValue

                playerData.diceResults.add(result)

                playerDataSource.insertRXPlayer(playerData)
            } else Completable.complete()

    private fun canExecute(playerData: PlayerData): Boolean =
            playerData.phase.isMain1() && !playerData.hasDicedEnough()

}