package de.bitb.spacerace.usecase.game.action.items

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.items.ItemDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.core.exceptions.ItemNotFoundException
import de.bitb.spacerace.core.exceptions.ItemNotUsableException
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import de.bitb.spacerace.usecase.game.check.CheckPlayerConfig
import de.bitb.spacerace.usecase.game.check.CheckPlayerPhaseUsecase
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class UseItemUsecase @Inject constructor(
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val checkPlayerPhaseUsecase: CheckPlayerPhaseUsecase,
        private val playerDataSource: PlayerDataSource,
        private val itemDataSource: ItemDataSource
) : ResultUseCase<UseItemResult, UseItemConfig> {
    override fun buildUseCaseSingle(params: UseItemConfig): Single<UseItemResult> =
            checkCurrentPlayerUsecase.buildUseCaseSingle(params.playerColor)
                    .map { getItem(params, it) }
                    .flatMap { checkPhase(it.first, it.second) }
                    .map { checkItemUsable(params, it.first, it.second) }
                    .map { useItem(params, it.first, it.second) }
                    .flatMap { finishUsing(it.first, it.second) }

    private fun getItem(params: UseItemConfig, playerData: PlayerData) =
            playerData to (params.getItem(playerData)
                    ?: throw ItemNotFoundException(params.itemType))

    private fun checkPhase(playerData: PlayerData, itemData: ItemData) =
            checkPlayerPhaseUsecase.buildUseCaseSingle(CheckPlayerConfig(
                    player = playerData.playerColor,
                    phaseToCheck = itemData.itemInfo.usablePhase)
            ).map { playerData to itemData }

    private fun checkItemUsable(
            params: UseItemConfig,
            playerData: PlayerData,
            itemData: ItemData
    ) =
            if (params.checkItemUsable(playerData, itemData)) playerData to itemData
            else throw ItemNotUsableException(itemData.itemInfo)

    private fun useItem(params: UseItemConfig, playerData: PlayerData, itemData: ItemData) =
            params.useItem(playerData, itemData)

    private fun finishUsing(playerData: PlayerData, itemData: ItemData): Single<UseItemResult> =
            Single.zip(
                    savePlayer(playerData),
                    getItem(itemData.id),
                    BiFunction<PlayerData, ItemData, UseItemResult> { player, item ->
                        UseItemResult(player, item)
                    })

    private fun savePlayer(playerData: PlayerData): Single<PlayerData> =
            playerDataSource.insertAndReturnRXPlayer(playerData).map { it.first() }

    private fun getItem(itemId: Long): Single<ItemData> =
            itemDataSource.getRXItems(itemId).map { it.first() }
}

data class UseItemConfig(
        val playerColor: PlayerColor,
        val itemType: ItemType,
        val getItem: (PlayerData) -> ItemData?,
        val checkItemUsable: (PlayerData, ItemData) -> Boolean,
        val useItem: (PlayerData, ItemData) -> Pair<PlayerData, ItemData>
)

data class UseItemResult(
        val playerData: PlayerData,
        val itemData: ItemData
)