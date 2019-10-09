package de.bitb.spacerace.usecase.game.action

import de.bitb.spacerace.database.items.*
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.exceptions.ItemNotFoundException
import de.bitb.spacerace.exceptions.ItemNotImplementedException
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.ItemInfo
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
            params.let { (playerColor, itemType) ->
                checkCurrentPlayerUsecase.buildUseCaseCompletable(playerColor)
                        .andThen(checkPhase(playerColor))
                        .flatMap { checkItemUsable(it, itemType) }
                        .flatMap { handleItem(it.first, it.second) }
                        .flatMap { (playerData, itemData) ->
                            Single.zip(
                                    savePlayer(playerData),
                                    getItem(itemData.id),
                                    BiFunction<PlayerData, ItemData, UseItemResult> { player, item ->
                                        UseItemResult(player, item)
                                    })
                        }
            }

    private fun checkPhase(playerColor: PlayerColor): Single<PlayerData> =
            checkPlayerPhaseUsecase.buildUseCaseSingle(
                    //TODO depending on item
                    CheckPlayerConfig(playerColor, listOf(Phase.MAIN1, Phase.MAIN2))
            )

    private fun checkItemUsable(playerData: PlayerData, itemInfo: ItemInfo): Single<Pair<PlayerData, ItemData>> =
            Single.create { emitter ->
                when (val item = playerData.storageItems.find { it.itemInfo == itemInfo }) {
                    null -> emitter.onError(ItemNotFoundException(itemInfo))
                    //TODO make item conditions -> also phase
                    else -> emitter.onSuccess(playerData to item)
                }
            }

    private fun handleItem(playerData: PlayerData, itemData: ItemData): Single<Pair<PlayerData, ItemData>> =
            Single.fromCallable {
                when (itemData.itemInfo) {
                    is EquipItem -> equipItem(playerData, itemData)
                    is AttachableItem -> attachItem(playerData, itemData)
                    is ActivableItem -> activateItem(playerData, itemData)
                    is DisposableItem -> activateItem(playerData, itemData)
                    else -> throw ItemNotImplementedException(itemData.itemInfo)
                } to itemData
            }

    private fun equipItem(playerData: PlayerData, itemData: ItemData): PlayerData =
            playerData.apply {
                equippedItems.add(itemData)//TODO do this in equip usecase
                storageItems.remove(itemData)
            }

    private fun attachItem(playerData: PlayerData, itemData: ItemData): PlayerData =
            playerData.apply {
                attachedItems.add(itemData) //TODO do this in attach usecase
            }

    private fun activateItem(playerData: PlayerData, itemData: ItemData): PlayerData =
            playerData.apply {
                activeItems.add(itemData)
                storageItems.remove(itemData)
            }

//    private fun useItem(playerData: PlayerData, itemData: ItemData): PlayerData =
//            playerData.apply { activeItems.add(itemData) }


    private fun savePlayer(playerData: PlayerData): Single<PlayerData> =
            playerDataSource.insertAndReturn(playerData).map { it.first() }

    private fun getItem(itemId: Long): Single<ItemData> =
            itemDataSource.getItems(itemId).map { it.first() }

}

data class UseItemConfig(
        val playerColor: PlayerColor,
        val itemInfo: ItemInfo
)

data class UseItemResult(
        val playerColor: PlayerData,
        val itemData: ItemData
)