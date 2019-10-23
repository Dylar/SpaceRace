package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.items.ItemDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ObserveRoundUsecase
@Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val itemDataSource: ItemDataSource
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
                players.map { it.activeItems }
                        .flatten()
                        .asSequence()
                        .onEach { it.itemInfo.charges-- }
                        .filter { it.itemInfo.charges < 1 }

        return players to usedItems.toList()
    }

    private fun endPlayerRound(players: List<PlayerData>): List<PlayerData> =
            players.onEach { player ->
                player.apply {
                    steps.clear()
                    diceResults.clear()
                    phase = Phase.END_ROUND
                    repeat(mines.size) { addRandomWin() }
                }
            }

    private fun saveData(player: List<PlayerData>, usedItems: List<ItemData>) =
            Observable.zip(
                    savePlayer(player),
                    saveItems(usedItems),
                    BiFunction<Boolean, Boolean, Boolean> { playerFinished, itemFinished ->
                        playerFinished && itemFinished
                    })

    private fun saveItems(usedItems: List<ItemData>): Observable<Boolean> =
            if (usedItems.isEmpty()) Observable.just(false)
            else itemDataSource.deleteRXItems(*usedItems.toTypedArray())
                    .andThen(Observable.just(true))

    private fun savePlayer(player: List<PlayerData>): Observable<Boolean> =
            if (player.isEmpty()) Observable.just(false)
            else playerDataSource
                    .insertAndReturnRXPlayer(*player.toTypedArray())
                    .map { player.isNotEmpty() }
                    .toObservable()
}