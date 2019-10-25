package de.bitb.spacerace.usecase.game.action.items

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.ItemType
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Single
import javax.inject.Inject

class AttachItemUsecase @Inject constructor(
        private val useItemUsecase: UseItemUsecase,
        private val mapDataSource: MapDataSource
) : ResultUseCase<UseItemResult, UseItemConfig> {

    override fun buildUseCaseSingle(params: UseItemConfig): Single<UseItemResult> =
            useItemUsecase.buildUseCaseSingle(UseItemConfig(
                    playerColor = params.playerColor,
                    itemType = params.itemType,
                    getItem = { getItem(params, it) },
                    checkItemUsable = { _, _ -> true },
                    useItem = { playerData, itemData -> useItem(playerData, itemData) }
            ))

    private fun getItem(params: UseItemConfig, playerData: PlayerData): ItemData? =
            playerData.storageItems.firstOrNull { it.itemInfo.type == params.itemType }

    private fun useItem(playerData: PlayerData, itemData: ItemData): Pair<PlayerData, ItemData> =
            playerData.apply {
                storageItems.remove(itemData)
                val field = playerData.positionField.target
                field.disposedItems.add(itemData)
                mapDataSource.insertDBField(field)
            } to itemData

}

data class AttachItemConfig(
        val playerColor: PlayerColor,
        val itemInfo: ItemType
)