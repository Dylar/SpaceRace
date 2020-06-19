package de.bitb.spacerace.usecase.game.action.items.shop

import de.bitb.spacerace.core.exceptions.BuyItemLowCreditException
import de.bitb.spacerace.core.exceptions.PlayerNotOnShopException
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.grafik.model.items.ItemInfo
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.items.getDefaultInfo
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import de.bitb.spacerace.usecase.game.check.CheckPlayerConfig
import de.bitb.spacerace.usecase.game.check.CheckPlayerPhaseUsecase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class BuyItemUsecase @Inject constructor(
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val checkPlayerPhaseUsecase: CheckPlayerPhaseUsecase,
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<BuyItemResult, BuyItemConfig> {

    override fun buildUseCaseSingle(params: BuyItemConfig): Single<BuyItemResult> =
            checkCurrentPlayerUsecase.buildUseCaseCompletable(params.playerColor)
                    .andThen(checkPhase(params.playerColor))
                    .flatMap { checkItemBuyable(it, params.itemType) }
                    .map { buyItem(it.first, it.second) }
                    .flatMap { saveData(it.first, it.second) }

    private fun checkItemBuyable(player: PlayerData, itemType: ItemType) =
            Single.create<Pair<PlayerData, ItemInfo>> { emitter ->
                val itemInfo = itemType.getDefaultInfo()
                when {
                    player.positionField.target.fieldType != FieldType.SHOP -> emitter.onError(PlayerNotOnShopException(player, itemInfo))
                    player.credits < itemInfo.price -> emitter.onError(BuyItemLowCreditException(player, itemInfo))
                    else -> emitter.onSuccess(player to itemInfo)
                }
            }

    private fun checkPhase(playerColor: PlayerColor): Single<PlayerData> =
            checkPlayerPhaseUsecase.buildUseCaseSingle(CheckPlayerConfig(
                    player = playerColor,
                    phaseToCheck = setOf(Phase.MAIN2))
            )

    private fun buyItem(player: PlayerData, itemInfo: ItemInfo): Pair<PlayerData, ItemData> {
        val item = ItemData.createItem(player, itemInfo)
        player.storageItems.add(item)
        player.credits -= itemInfo.price
        return player to item
    }

    private fun saveData(player: PlayerData, item: ItemData): Single<BuyItemResult> =
            playerDataSource.insertAndReturnRXPlayer(player)
                    .map { players ->
                        val savedPlayer = players.first()
                        val savedItem = savedPlayer.storageItems.first { it.id == item.id }
                        BuyItemResult(savedPlayer, savedItem)
                    }
}

data class BuyItemConfig(
        val playerColor: PlayerColor,
        val itemType: ItemType
)

data class BuyItemResult(
        val playerData: PlayerData,
        val itemData: ItemData
)