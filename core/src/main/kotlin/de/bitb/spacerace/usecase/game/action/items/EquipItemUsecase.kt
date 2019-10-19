package de.bitb.spacerace.usecase.game.action.items

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.items.ItemDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.ItemNotFoundException
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckItemConfig
import de.bitb.spacerace.usecase.game.check.CheckItemPhaseUsecase
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class EquipItemUsecase @Inject constructor(
        private val checkItemPhaseUsecase: CheckItemPhaseUsecase,
        private val playerDataSource: PlayerDataSource,
        private val itemDataSource: ItemDataSource
) : ResultUseCase<EquipItemResult, EquipItemConfig> {

    override fun buildUseCaseSingle(params: EquipItemConfig): Single<EquipItemResult> =
            getItem(params)
                    .flatMap {
                        checkItemPhaseUsecase.buildUseCaseSingle(CheckItemConfig(
                                playerColor = params.playerColor,
                                itemData = it))
                    }
                    .flatMap { checkItemUsable(it.playerData, it.itemData, it.itemData.itemInfo) }
                    .flatMap { equipItem(it.first, it.second, params.equip) }
                    .flatMap { (playerData, itemData) ->
                        Single.zip(
                                savePlayer(playerData),
                                getItem(itemData.id),
                                BiFunction<PlayerData, ItemData, EquipItemResult> { player, item ->
                                    EquipItemResult(player, item)
                                })
                    }

    private fun checkItemUsable(playerData: PlayerData, itemData: ItemData?, itemInfo: ItemInfo): Single<Pair<PlayerData, ItemData>> =
            Single.create { emitter ->
                itemData?.let { emitter.onSuccess(playerData to it) }
                        ?: kotlin.run { emitter.onError(ItemNotFoundException(itemInfo)) }
            }

    private fun equipItem(playerData: PlayerData, itemData: ItemData, equip: Boolean): Single<Pair<PlayerData, ItemData>> =
            Single.fromCallable {
                playerData.apply {
                    (if (equip) storageItems else equippedItems).remove(itemData)
                    (if (equip) equippedItems else storageItems).add(itemData)
                } to itemData
            }

    private fun savePlayer(playerData: PlayerData): Single<PlayerData> =
            playerDataSource.insertAndReturn(playerData).map { it.first() }

    private fun getItem(itemId: Long): Single<ItemData> =
            itemDataSource.getItems(itemId).map { it.first() }

    private fun getItem(equipInfo: EquipItemConfig): Single<ItemData?> =
            playerDataSource.getByColor(equipInfo.playerColor)
                    .map { it.first() }
                    .map { playerData ->
                        when {
                            equipInfo.equip -> playerData.storageItems
                            else -> playerData.equippedItems
                        }.find { it.itemInfo.name == equipInfo.itemInfo.name }
                    }

}


data class EquipItemConfig(
        val playerColor: PlayerColor,
        val itemInfo: ItemInfo,
        val equip: Boolean = true
)

data class EquipItemResult(
        val player: PlayerData,
        val itemData: ItemData
)