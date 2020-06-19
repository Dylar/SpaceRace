package de.bitb.spacerace.usecase.game.action.items

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class EquipItemUsecase @Inject constructor(
        private val useItemUsecase: UseItemUsecase
) : ResultUseCase<UseItemResult, EquipItemConfig> {

    override fun buildUseCaseSingle(params: EquipItemConfig): Single<UseItemResult> =
            useItemUsecase.buildUseCaseSingle(UseItemConfig(
                    playerColor = params.playerColor,
                    itemType = params.itemType,
                    getItem = { getItem(params, it) },
                    checkItemUsable = { _, _ -> true },
                    useItem = { playerData, itemData -> useItem(params, playerData, itemData) }
            ))

    private fun useItem(params: EquipItemConfig, playerData: PlayerData, itemData: ItemData): Pair<PlayerData, ItemData> =
            playerData.apply {
                (if (params.equip) storageItems else equippedItems).remove(itemData)
                (if (params.equip) equippedItems else storageItems).add(itemData)
            } to itemData

    private fun getItem(
            params: EquipItemConfig,
            playerData: PlayerData
    ): ItemData? =
            when {
                params.equip -> playerData.storageItems
                else -> playerData.equippedItems
            }.firstOrNull { item -> item.itemInfo.type == params.itemType }
}

data class EquipItemConfig(
        val playerColor: PlayerColor,
        val itemType: ItemType,
        val equip: Boolean = true
)