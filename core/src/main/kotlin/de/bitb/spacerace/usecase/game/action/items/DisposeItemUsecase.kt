package de.bitb.spacerace.usecase.game.action.items

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Single
import javax.inject.Inject

class DisposeItemUsecase @Inject constructor(
        private val useItemUsecase: UseItemUsecase,
        private val mapDataSource: MapDataSource
) : ResultUseCase<UseItemResult, DisposeItemConfig> {

    override fun buildUseCaseSingle(params: DisposeItemConfig): Single<UseItemResult> =
            useItemUsecase.buildUseCaseSingle(UseItemConfig(
                    playerColor = params.playerColor,
                    itemInfo = params.itemInfo,
                    getItem = { getItem(params, it) },
                    checkItemUsable = { _, _ -> true },
                    useItem = { playerData, itemData -> useItem(playerData, itemData) }
            ))

    private fun getItem(params: DisposeItemConfig, playerData: PlayerData): ItemData? =
            playerData.storageItems.firstOrNull { it.itemInfo.name == params.itemInfo.name }

    private fun useItem(playerData: PlayerData, itemData: ItemData): Pair<PlayerData, ItemData> =
            playerData.apply {
                storageItems.remove(itemData)
                val field = playerData.positionField.target
                field.disposedItems.add(itemData)
                mapDataSource.insertDBField(field)
            } to itemData

}

data class DisposeItemConfig(
        val playerColor: PlayerColor,
        val itemInfo: ItemInfo
)