package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.usecase.dispender.PlayerColorDispenser
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import io.reactivex.Observable
import javax.inject.Inject

class ObserveCurrentPlayerUseCase @Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val playerColorDispenser: PlayerColorDispenser
) : StreamUseCaseNoParams<PlayerData> {

    override fun buildUseCaseObservable(): Observable<PlayerData> {
        return playerColorDispenser.publisher
                .switchMap { playerDataSource.observePlayerByColor(it) }
                .map { it.first() }
    }

}