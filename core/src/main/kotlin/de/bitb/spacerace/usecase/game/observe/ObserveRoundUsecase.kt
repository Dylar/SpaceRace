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
) : StreamUseCaseNoParams<Boolean> {

    override fun buildUseCaseObservable(): Observable<Boolean> =
            playerDataSource.observeAllPlayer()
                    .map(::filterPlayer)
                    .map(::endPlayerRound)
                    .map(::triggerItems)
                    .flatMap { saveData(it.first, it.second) }

    private fun filterPlayer(player: List<PlayerData>) =
            if (player.all { it.phase.isEndTurn() }) player
            else listOf()

    private fun triggerItems(players: List<PlayerData>): Pair<List<PlayerData>, List<ItemData>> {
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

        return players to usedItems
    }

    private fun endPlayerRound(players: List<PlayerData>): List<PlayerData> =
            players.onEach { player ->
                player.apply {
                    clearTurn()
                    phase = Phase.END_ROUND
                    repeat(mines.size) { addRandomWin() }
                }
            }

    private fun saveData(player: List<PlayerData>, usedItems: List<ItemData>) =
            Observable.zip(
                    savePlayer(player),
                    deleteItems(usedItems),
                    BiFunction<List<PlayerData>, List<ItemData>, Boolean> { finishPlayer, finishItems ->
                        if (finishPlayer.isEmpty() && finishItems.isEmpty()) false
                        else {
                            true
                        }
                    })

    private fun deleteItems(usedItems: List<ItemData>): Observable<List<ItemData>> =
            if (usedItems.isEmpty()) Observable.just(usedItems)
            else itemDataSource.deleteRXItems(*usedItems.toTypedArray())
                    .andThen(Observable.just(usedItems))

    private fun savePlayer(player: List<PlayerData>): Observable<List<PlayerData>>? =
            if (player.isEmpty()) Observable.just(player)
            else playerDataSource
                    .insertAndReturnRXPlayer(*player.toTypedArray())
                    .toObservable()
}