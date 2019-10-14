package de.bitb.spacerace.usecase.game.check

import de.bitb.spacerace.database.items.*
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.ItemNotFoundException
import de.bitb.spacerace.exceptions.ItemNotImplementedException
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Single
import javax.inject.Inject

class CheckItemPhaseUsecase @Inject constructor(
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val checkPlayerPhaseUsecase: CheckPlayerPhaseUsecase
) : ResultUseCase<CheckItemResult, CheckItemConfig> {
    override fun buildUseCaseSingle(params: CheckItemConfig): Single<CheckItemResult> =
            params.let { (playerColor, itemData) ->
                checkCurrentPlayerUsecase.buildUseCaseCompletable(playerColor)
                        .andThen(checkPhase(playerColor, itemData))
                        .map { CheckItemResult(it, itemData) }
            }

    private fun checkPhase(playerColor: PlayerColor, itemData: ItemData): Single<PlayerData> =
            checkPlayerPhaseUsecase.buildUseCaseSingle(CheckPlayerConfig(
                    player = playerColor,
                    phaseToCheck = itemData.itemInfo.usablePhase)
            )

}

data class CheckItemConfig(
        val playerColor: PlayerColor,
        val itemData: ItemData
)

data class CheckItemResult(
        val playerData: PlayerData,
        val itemData: ItemData
)