package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ExecuteUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
import io.reactivex.Completable
import io.reactivex.CompletableSource
import javax.inject.Inject
import kotlin.math.absoluteValue

class DiceUsecase @Inject constructor(
        private val getPlayerUsecase: GetPlayerUsecase,
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val playerDataSource: PlayerDataSource,
        private val graphicController: GraphicController
) : ExecuteUseCase<Pair<PlayerColor, Int>> {

    override fun buildUseCaseCompletable(params: Pair<PlayerColor, Int>) =
            params.let { (playerColor, maxResult) ->
                checkCurrentPlayerUsecase.buildUseCaseCompletable(playerColor)
                        .andThen(getPlayerUsecase.buildUseCaseSingle(playerColor))
                        .flatMapCompletable { dice(it, maxResult) }
            }

    private fun dice(playerData: PlayerData, maxResult: Int): Completable =
            if (canExecute(playerData)) {
                val result =
                        if (maxResult > 0) {
                            (Math.random() * maxResult).toInt() + 1
                        } else maxResult.absoluteValue

                playerData.diceResults.add(result)

                playerDataSource.insert(playerData)
            } else Completable.complete()

    private fun canExecute(playerData: PlayerData): Boolean =
            if (playerData.phase.isMain1()) {
                val items = graphicController.getPlayerItems(playerData.playerColor)
                val diceCharges = 1 + items.multiDiceItem.sumBy { it.getAmount() }
                playerData.diceResults.size < diceCharges
            } else false

}