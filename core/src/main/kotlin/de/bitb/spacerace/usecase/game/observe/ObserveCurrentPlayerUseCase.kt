package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.core.PlayerColorDispender
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import io.reactivex.Observable
import javax.inject.Inject

class ObserveCurrentPlayerUseCase @Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val playerColorDispender: PlayerColorDispender
) : StreamUseCaseNoParams<PlayerData> {

    override fun buildUseCaseObservable(): Observable<PlayerData> {
        return playerColorDispender.publisher
                .switchMap { playerDataSource.observeByColor(it) }
                .map { it.first() }
    }

}