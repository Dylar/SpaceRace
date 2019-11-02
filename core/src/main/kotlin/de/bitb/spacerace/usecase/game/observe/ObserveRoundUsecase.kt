package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.items.ItemDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import de.bitb.spacerace.usecase.dispender.RemoveItemConfig
import de.bitb.spacerace.usecase.dispender.RemoveItemDispenser
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ObserveRoundUsecase
@Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val itemDataSource: ItemDataSource,
        private val removeItemDispenser: RemoveItemDispenser
//        private val moveItemDispenser: MoveItemDispenser
) : StreamUseCaseNoParams<ObserveRoundResult> {

    override fun buildUseCaseObservable(): Observable<ObserveRoundResult> =
            playerDataSource.observeAllPlayer()
                    .flatMap { player ->
                        val result = ObserveRoundResult(player)
                                .apply { roundEnding = player.all { it.phase.isEndTurn() } }
                        if (result.roundEnding) endRound(result)
                        else Observable.fromCallable { result }
                    }

    private fun endRound(result: ObserveRoundResult): Observable<ObserveRoundResult> =
            Observable.fromCallable {
                moveItems()
                result.apply {
                    endPlayerRound(player)
                    usedItems = decayItems(player)
                }
            }.flatMap { saveData(it) }
//                    .map(::endPlayerRound)
//                    .map(::decayItems)
//                    .flatMap { saveData(it.first, it.second) }
//                    .map(::moveItems)
//                    .onErrorReturn { it !is RoundNotEnding }

    private fun moveItems() {

    }

    private fun endPlayerRound(players: List<PlayerData>) =
            players.forEach { player ->
                player.apply {
                    clearTurn()
                    phase = Phase.END_ROUND
                    repeat(mines.size) { addRandomWin() }
                }
            }

    private fun decayItems(players: List<PlayerData>): MutableList<ItemData> {
        val usedItems =
                players.asSequence()
                        .map { it.activeItems }
                        .flatten()
                        .onEach { it.itemInfo.charges-- }
                        .filter { it.itemInfo.charges < 1 }.toMutableList()

        players.forEach { player ->
            val expiredItems = player.attachedItems.asSequence()
                    .onEach { it.itemInfo.charges-- }
                    .filter { it.itemInfo.charges < 1 }.toList()

            if (expiredItems.isNotEmpty()) {
                removeItemDispenser.publishUpdate(RemoveItemConfig(playerData = player, items = expiredItems))
                usedItems.addAll(expiredItems)
            }
        }

        players.forEach { it.activeItems.removeAll(usedItems) }

        return usedItems
    }

    private fun saveData(result: ObserveRoundResult) =
            Observable.zip(
                    savePlayer(result.player),
                    deleteItems(result.usedItems),
                    BiFunction<List<PlayerData>, List<ItemData>, ObserveRoundResult> { finishPlayer, finishItems ->
                        result.apply {
                            player = finishPlayer
                            usedItems = finishItems
                        }
                    })

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
        var player: List<PlayerData> = emptyList(),
        var usedItems: List<ItemData> = emptyList()
) {
    var roundEnding: Boolean = false
}