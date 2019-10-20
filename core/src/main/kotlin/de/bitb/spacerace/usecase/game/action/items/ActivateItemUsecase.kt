package de.bitb.spacerace.usecase.game.action.items

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.items.ItemDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckItemConfig
import de.bitb.spacerace.usecase.game.check.CheckItemPhaseUsecase
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ActivateItemUsecase @Inject constructor(
        private val checkItemPhaseUsecase: CheckItemPhaseUsecase,
        private val playerDataSource: PlayerDataSource,
        private val itemDataSource: ItemDataSource
) : ResultUseCase<ActivateItemResult, ActivateItemConfig> {

    override fun buildUseCaseSingle(params: ActivateItemConfig): Single<ActivateItemResult> =
            ItemHelper.getItem(playerDataSource, params.playerColor, params.itemInfo) { it.storageItems }
                    .flatMap {
                        checkItemPhaseUsecase.buildUseCaseSingle(CheckItemConfig(
                                playerColor = params.playerColor,
                                itemData = it))
                    }
                    .flatMap { checkItemUsable(it.playerData, it.itemData, it.itemData.itemInfo) }
                    .flatMap { activateItem(it.first, it.second) }
                    .flatMap { (playerData, itemData) ->
                        Single.zip(
                                savePlayer(playerData),
                                getItem(itemData.id),
                                BiFunction<PlayerData, ItemData, ActivateItemResult> { player, item ->
                                    ActivateItemResult(player, item)
                                })
                    }

    private fun checkItemUsable(playerData: PlayerData, itemData: ItemData, itemInfo: ItemInfo): Single<Pair<PlayerData, ItemData>> =
            Single.just(playerData to itemData)

    private fun activateItem(playerData: PlayerData, itemData: ItemData): Single<Pair<PlayerData, ItemData>> =
            Single.fromCallable {
                playerData.apply {
                    storageItems.remove(itemData)
                    activeItems.add(itemData)
                } to itemData
            }

    private fun savePlayer(playerData: PlayerData): Single<PlayerData> =
            playerDataSource.insertAndReturn(playerData).map { it.first() }

    private fun getItem(itemId: Long): Single<ItemData> =
            itemDataSource.getItems(itemId).map { it.first() }

}


data class ActivateItemConfig(
        val playerColor: PlayerColor,
        val itemInfo: ItemInfo
)

data class ActivateItemResult(
        val player: PlayerData,
        val itemData: ItemData
)