package de.bitb.spacerace.usecase.game.action.items

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.ItemInfo
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Single
import javax.inject.Inject

class ActivateItemUsecase @Inject constructor(
        private val useItemUsecase: UseItemUsecase
) : ResultUseCase<UseItemResult, ActivateItemConfig> {

    override fun buildUseCaseSingle(params: ActivateItemConfig): Single<UseItemResult> =
            useItemUsecase.buildUseCaseSingle(UseItemConfig(
                    playerColor = params.playerColor,
                    itemInfo = params.itemInfo,
                    getItem = { it.storageItems.firstOrNull { item -> item.itemInfo.name == params.itemInfo.name } },
                    checkItemUsable = { _, _ -> true },
                    useItem = { playerData, itemData -> activateItem(playerData, itemData) }
            ))

    private fun activateItem(playerData: PlayerData, itemData: ItemData): Pair<PlayerData, ItemData> =
            playerData.apply {
                storageItems.remove(itemData)
                activeItems.add(itemData)
            } to itemData

}

data class ActivateItemConfig(
        val playerColor: PlayerColor,
        val itemInfo: ItemInfo
)