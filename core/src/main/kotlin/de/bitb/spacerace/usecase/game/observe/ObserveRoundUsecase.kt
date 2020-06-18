package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.items.ItemDataSource
import de.bitb.spacerace.database.items.MovableItem
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import de.bitb.spacerace.usecase.dispender.MoveItemConfig
import de.bitb.spacerace.usecase.dispender.MoveItemDispenser
import de.bitb.spacerace.usecase.dispender.RemoveItemConfig
import de.bitb.spacerace.usecase.dispender.RemoveItemDispenser
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import io.reactivex.rxjava3.functions.Function3

class ObserveRoundUsecase
@Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val itemDataSource: ItemDataSource,
        private val mapDataSource: MapDataSource,
        private val removeItemDispenser: RemoveItemDispenser,
        private val moveItemDispenser: MoveItemDispenser
) : StreamUseCaseNoParams<ObserveRoundResult> {

    //I am not beautiful but still lovable
    override fun buildUseCaseObservable(): Observable<ObserveRoundResult> =
            playerDataSource.observeAllPlayer()
                    .flatMap { player ->
                        val result = ObserveRoundResult(player)
                                .apply { roundEnding = player.all { it.phase.isEndTurn() } }
                        if (result.roundEnding) endRound(result)
                        else Observable.fromCallable { result }
                    }

    private fun endRound(result: ObserveRoundResult): Observable<ObserveRoundResult> =
            mapDataSource.getRXFieldWithMovableItems()
                    .map { fields ->
                        moveItems(fields)
                        endPlayerRound(result)
                        decayItems(result)
                    }.toObservable()
                    .flatMap { saveData(it) }

    private fun moveItems(fields: List<FieldData>) {
        val moveInfos = mutableListOf<MoveItemConfig>()
        fields.forEach { fromField ->
            fromField.disposedItems.asSequence()
                    .filter { it.itemInfo is MovableItem }
                    .forEach { item ->
                        val toField = fromField.connections.random() //TODO do this in settings
                        moveInfos.add(MoveItemConfig(fromField, toField, item))
                    }
        }

        moveInfos.forEach {
            it.fromField.disposedItems.remove(it.item)
            it.toField.disposedItems.add(it.item)
            mapDataSource.insertDBField(it.fromField, it.toField)
        }

        moveItemDispenser.publishUpdate(moveInfos)
    }

    private fun endPlayerRound(result: ObserveRoundResult) =
            result.apply {
                players.forEach { player ->
                    player.apply {
                        clearTurn()
                        phase = Phase.END_ROUND
                        repeat(mines.size) { addRandomWin() }
                    }
                }
            }

    private fun decayItems(result: ObserveRoundResult): ObserveRoundResult {
        result.removedItems =
                result.players.asSequence()
                        .map { it.activeItems }
                        .flatten()
                        .onEach {
                            it.itemInfo.charges--
                            result.updatedItems.add(it)
                        }
                        .filter { it.itemInfo.charges < 1 }.toMutableList()
        result.players.forEach { it.activeItems.removeAll(result.removedItems) }

        val expiredItems = result.players
                .asSequence()
                .map { player ->
                    player.attachedItems.asSequence()
                            .onEach {
                                it.itemInfo.charges--
                                result.updatedItems.add(it)
                            }
                            .filter { it.itemInfo.charges < 1 }
                            .toList()
                            .also { items ->
                                if (items.isNotEmpty()) {
                                    removeItemDispenser.publishUpdate(RemoveItemConfig(playerData = player, items = items))
                                }
                            }
                }.flatten().toList()
        result.players.forEach { it.attachedItems.removeAll(expiredItems) }

        result.removedItems.addAll(expiredItems)
        return result
    }

    private fun saveData(result: ObserveRoundResult): Observable<ObserveRoundResult> =
            Observable.zip(
                    savePlayer(result.players),
                    saveItems(result.updatedItems),
                    deleteItems(result.removedItems),
                    Function3<List<PlayerData>, List<ItemData>, List<ItemData>, ObserveRoundResult> { finishPlayer, removedItems, updatedItems ->
                        result.also {
                            it.players = finishPlayer
                            it.removedItems = removedItems.toMutableList()
                            it.updatedItems = updatedItems.toMutableList()
                        }
                    })

    private fun saveItems(usedItems: List<ItemData>): Observable<List<ItemData>> =
            if (usedItems.isEmpty()) Observable.just(usedItems)
            else itemDataSource.insertRXItems(*usedItems.toTypedArray()).toObservable()

    private fun deleteItems(usedItems: List<ItemData>): Observable<List<ItemData>> =
            if (usedItems.isEmpty()) Observable.just(usedItems)
            else itemDataSource.deleteRXItems(*usedItems.toTypedArray())
                    .andThen(Observable.just(usedItems))

    private fun savePlayer(player: List<PlayerData>): Observable<List<PlayerData>> =
            playerDataSource
                    .insertAndReturnRXPlayer(*player.toTypedArray())
                    .toObservable()
}

data class ObserveRoundResult(
        var players: List<PlayerData> = emptyList(),
        var removedItems: MutableList<ItemData> = mutableListOf(),
        var updatedItems: MutableList<ItemData> = mutableListOf()
) {
    var roundEnding: Boolean = false
}