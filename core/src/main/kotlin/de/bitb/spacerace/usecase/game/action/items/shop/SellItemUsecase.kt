package de.bitb.spacerace.usecase.game.action.items.shop

import de.bitb.spacerace.config.ITEM_SELL_MOD
import de.bitb.spacerace.core.exceptions.ItemNotFoundException
import de.bitb.spacerace.core.exceptions.PlayerNotOnShopException
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.items.getDefaultInfo
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.check.CheckCurrentPlayerUsecase
import de.bitb.spacerace.usecase.game.check.CheckPlayerConfig
import de.bitb.spacerace.usecase.game.check.CheckPlayerPhaseUsecase
import io.reactivex.Single
import javax.inject.Inject

class SellItemUsecase @Inject constructor(
        private val checkCurrentPlayerUsecase: CheckCurrentPlayerUsecase,
        private val checkPlayerPhaseUsecase: CheckPlayerPhaseUsecase,
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<SellItemResult, SellItemConfig> {

    override fun buildUseCaseSingle(params: SellItemConfig): Single<SellItemResult> =
            checkCurrentPlayerUsecase.buildUseCaseCompletable(params.playerColor)
                    .andThen(checkPhase(params.playerColor))
                    .flatMap { checkItemSellable(it, params.itemType) }
                    .map { sellItem(it.first, it.second) }
                    .flatMap { saveData(it.first, it.second) }

    private fun checkPhase(playerColor: PlayerColor): Single<PlayerData> =
            checkPlayerPhaseUsecase.buildUseCaseSingle(CheckPlayerConfig(
                    player = playerColor,
                    phaseToCheck = setOf(Phase.MAIN2))
            )

    private fun checkItemSellable(player: PlayerData, itemType: ItemType) =
            Single.create<Pair<PlayerData, ItemData>> { emitter ->
                val item = player.storageItems.asSequence()
                        .filter { it.itemInfo.type == itemType }
                        .minBy { it.itemInfo.charges }
                when {
                    item == null -> emitter.onError(ItemNotFoundException(itemType))
                    player.positionField.target.fieldType != FieldType.SHOP -> emitter.onError(PlayerNotOnShopException(player, itemType.getDefaultInfo()))
                    else -> emitter.onSuccess(player to item)
                }
            }

    private fun sellItem(player: PlayerData, itemData: ItemData): Pair<PlayerData, ItemData> {
        player.storageItems.remove(itemData)
        player.credits += (itemData.itemInfo.price * ITEM_SELL_MOD).toInt()
        return player to itemData
    }

    private fun saveData(player: PlayerData, item: ItemData): Single<SellItemResult> =
//            remove item ? TODO
            playerDataSource.insertAndReturnRXPlayer(player)
                    .map { players ->
                        val savedPlayer = players.first()
                        SellItemResult(savedPlayer, item)
                    }
}

data class SellItemConfig(
        val playerColor: PlayerColor,
        val itemType: ItemType
)

data class SellItemResult(
        val playerData: PlayerData,
        val itemData: ItemData
)