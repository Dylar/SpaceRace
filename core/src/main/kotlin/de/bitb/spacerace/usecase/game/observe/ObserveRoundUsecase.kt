package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import io.reactivex.Observable
import javax.inject.Inject

class ObserveRoundUsecase
@Inject constructor(
        private val playerDataSource: PlayerDataSource
) : StreamUseCaseNoParams<Boolean> {

    override fun buildUseCaseObservable(): Observable<Boolean> =
            playerDataSource.observeAllPlayer()
                    .map(::filterPlayer)
                    .flatMap(::endRound)
                    .flatMap(::updatePlayer)

    private fun filterPlayer(player: List<PlayerData>) =
            if (player.all { it.phase.isEndTurn() }) player
            else listOf()

    private fun endRound(players: List<PlayerData>): Observable<List<PlayerData>> =
            Observable.fromCallable {
                players.onEach { player ->
                    player.nextRound()
                    repeat(player.mines.size) { player.addRandomWin() }
                }
            }

    private fun updatePlayer(player: List<PlayerData>) =
            if (player.isEmpty()) Observable.just(false)
            else playerDataSource
                    .insertAndReturnRXPlayer(*player.toTypedArray())
                    .map { player.isNotEmpty() }
                    .toObservable()
}