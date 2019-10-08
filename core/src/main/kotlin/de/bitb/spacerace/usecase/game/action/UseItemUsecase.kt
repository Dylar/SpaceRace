package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.ItemType
import de.bitb.spacerace.model.items.NONE_ITEMTYPE
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import de.bitb.spacerace.usecase.game.check.CheckPlayerConfig
import de.bitb.spacerace.usecase.game.check.CheckPlayerPhaseUsecase
import io.reactivex.Single
import javax.inject.Inject

class UseItemUsecase @Inject constructor(
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val checkPlayerPhaseUsecase: CheckPlayerPhaseUsecase,
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<UseItemResult, UseItemConfig> {

    override fun buildUseCaseSingle(params: UseItemConfig) =
            params.let { (playerColor, itemData) ->
                checkCurrentPlayerUsecase.buildUseCaseCompletable(playerColor)
                        .andThen(checkPhase(playerColor))
                        .flatMap { checkItemUsable(it, itemData) }
                        .flatMap { useItem(it.first, it.second) }
            }

    private fun checkItemUsable(playerData: PlayerData, itemData: ItemData): Single<Pair<PlayerData, ItemData>> =
            Single.create { emitter ->
                //TODO make item conditions -> also phase
                emitter.onSuccess(playerData to itemData)
            }


    private fun useItem(playerData: PlayerData, itemData: ItemData): Single<UseItemResult> =
            Single.fromCallable {
                val usedItem = when (itemData.itemType) {
                    is NONE_ITEMTYPE -> itemData
                    is ItemType.EXTRA_FUEL -> itemData
                    is ItemType.SPECIAL_FUEL -> itemData
                    is ItemType.SPEED_BOOST -> itemData
                    is ItemType.CLEAN_DROID -> itemData
                    is ItemType.SLOW_MINE -> itemData
                    is ItemType.MOVING_MINE -> itemData
                    is ItemType.ION_ENGINE -> itemData
                    is ItemType.SHIP_SPEEDER -> itemData
                    is ItemType.SHIP_RAIDER -> itemData
                    is ItemType.SHIP_BUMPER -> itemData
                }

                UseItemResult(playerDataSource.getDBByColor(playerData.playerColor).first(), usedItem)
            }

    private fun checkPhase(playerColor: PlayerColor): Single<PlayerData> =
            checkPlayerPhaseUsecase.buildUseCaseSingle(
                    CheckPlayerConfig(playerColor, listOf(Phase.MAIN1, Phase.MAIN2))
            )

}

data class UseItemConfig(
        val playerColor: PlayerColor,
        val itemData: ItemData
)

data class UseItemResult(
        val playerColor: PlayerData,
        val itemData: ItemData
)