package de.bitb.spacerace.usecase.game.action.items

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Single
import javax.inject.Inject

class ActivateItemUsecase @Inject constructor(
        private val useItemUsecase: UseItemUsecase
) : ResultUseCase<UseItemResult, ActivateItemConfig> {

    override fun buildUseCaseSingle(params: ActivateItemConfig): Single<UseItemResult> =
            useItemUsecase.buildUseCaseSingle(UseItemConfig(
                    playerColor = params.playerColor,
                    itemType = params.itemType,
                    getItem = { getItem(params, it) },
                    checkItemUsable = { _, _ -> true },
                    useItem = { playerData, itemData -> useItem(playerData, itemData) }
            ))

    private fun getItem(params: ActivateItemConfig, playerData: PlayerData): ItemData? =
            playerData.storageItems.asSequence()
                    .filter { it.itemInfo.type == params.itemType }
                    .maxBy { it.itemInfo.charges }


    private fun useItem(playerData: PlayerData, itemData: ItemData): Pair<PlayerData, ItemData> =
            playerData.apply {
                storageItems.remove(itemData)
                activeItems.add(itemData)
            } to itemData

}

data class ActivateItemConfig(
        val playerColor: PlayerColor,
        val itemType: ItemType
)