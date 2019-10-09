package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import io.reactivex.Observable
import javax.inject.Inject

class ObserveRoundUsecase
@Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val mapDataSource: MapDataSource
) : StreamUseCaseNoParams<Boolean> {

    override fun buildUseCaseObservable(): Observable<Boolean> =
            playerDataSource.observeAllObserver()
                    .map(::filterPlayer)
                    .flatMap(::endRound)
                    .flatMap(::updatePlayer)

    private fun filterPlayer(player: List<PlayerData>) =
            if (player.all { it.phase.isEndTurn() }) player
            else mutableListOf()

    private fun endRound(players: List<PlayerData>): Observable<List<PlayerData>> =
            if (players.isEmpty()) Observable.just(players)
            else Observable.create { emitter ->
                players.forEach { player ->
                    player.nextRound()
                    repeat(player.mines.size) { player.addRandomWin() }
                }
                emitter.onNext(players)
            }


    private fun updatePlayer(player: List<PlayerData>) =
            if (player.isEmpty()) Observable.just(false)
            else playerDataSource
                    .insertAndReturn(*player.toTypedArray())
                    .map { player.isNotEmpty() }
                    .toObservable()
}