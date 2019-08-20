package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.objecthandling.DEFAULT
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ExecuteUseCase
import io.reactivex.Completable
import javax.inject.Inject

class MoveUsecase @Inject constructor(
        private val playerController: PlayerController,
        private val playerDataSource: PlayerDataSource
) : ExecuteUseCase<Pair<PlayerColor, Int>>,
        DefaultFunction by DEFAULT {

    override fun buildUseCaseCompletable(params: Pair<PlayerColor, Int>) =
            params.let { (playerColor, maxResult) ->
                playerDataSource
                        .getByColor(playerColor)
                        .map { it.first() }
                        .flatMapCompletable { dice(it, maxResult) }
            }

    private fun dice(playerData: PlayerData, maxResult: Int): Completable {
        if (canExecute(playerData)) {
            val result = (Math.random() * maxResult).toInt() + 1
            playerData.diceResults.add(result)
        }

        return playerDataSource.insertAll(playerData)
    }

    private fun canExecute(playerData: PlayerData): Boolean {
        if (!playerData.phase.isMain1()) {
            return false
        }
        val items = getPlayerItems(playerController, playerData.playerColor)
        val diceCharges = 1 + items.multiDiceItem.sumBy { it.getAmount() }
        return playerData.diceResults.size < diceCharges
    }

}