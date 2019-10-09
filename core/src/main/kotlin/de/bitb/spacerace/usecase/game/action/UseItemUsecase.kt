package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.database.items.*
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.ItemNotImplemented
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerColor
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

    override fun buildUseCaseSingle(params: UseItemConfig) =
            params.let { (playerColor, itemData) ->
                checkCurrentPlayerUsecase.buildUseCaseCompletable(playerColor)
                        .andThen(checkPhase(playerColor))
                        .flatMap { checkItemUsable(it, itemData) }
                        .flatMap { handleItem(it, itemData) }
                        .flatMap {
                            Single.zip(
                                    savePlayer(it.first),
                                    getItem(itemData.id),
                                    BiFunction<PlayerData, ItemData, UseItemResult> { playerData, itemData ->
                                        UseItemResult(playerData, itemData)
                                    })
                        }
            }

    private fun getItem(itemId: Long): Single<ItemData> = itemDataSource.getItems(itemId).map { it.first() }

    private fun savePlayer(playerData: PlayerData): Single<PlayerData> =
            playerDataSource.insertAndReturn(playerData).map { it.first() }


    private fun checkPhase(playerColor: PlayerColor): Single<PlayerData> =
            checkPlayerPhaseUsecase.buildUseCaseSingle(
                    CheckPlayerConfig(playerColor, listOf(Phase.MAIN1, Phase.MAIN2))
            )

    private fun checkItemUsable(playerData: PlayerData, itemData: ItemData): Single<PlayerData> =
            Single.create { emitter ->
                //TODO make item conditions -> also phase
                emitter.onSuccess(playerData)
            }


    private fun handleItem(playerData: PlayerData, itemData: ItemData): Single<Pair<PlayerData, ItemData>> =
            Single.fromCallable {
                when (itemData.itemType) {
                    is EquipItem -> equipItem(playerData, itemData)
                    is AttachableItem -> attachItem(playerData, itemData)
                    is ActivableItem -> activateItem(playerData, itemData)
                    else -> throw ItemNotImplemented(itemData.itemType)
                } to itemData
            }

    private fun equipItem(playerData: PlayerData, itemData: ItemData): PlayerData =
            playerData.apply { equippedItems.add(itemData) }

    private fun attachItem(playerData: PlayerData, itemData: ItemData): PlayerData =
            playerData.apply { attachedItems.add(itemData) }

    private fun activateItem(playerData: PlayerData, itemData: ItemData): PlayerData =
            playerData.apply { activeItems.add(itemData) }

    private fun useItem(playerData: PlayerData, itemData: ItemData): PlayerData =
            playerData.apply { activeItems.add(itemData) }
}

data class UseItemConfig(
        val playerColor: PlayerColor,
        val itemData: ItemData
)

data class UseItemResult(
        val playerColor: PlayerData,
        val itemData: ItemData
)