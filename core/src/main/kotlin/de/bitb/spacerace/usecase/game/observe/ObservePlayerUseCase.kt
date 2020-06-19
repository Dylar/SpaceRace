package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.StreamUseCase
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class ObservePlayerUseCase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : StreamUseCase<PlayerData, PlayerColor> {

    override fun buildUseCaseObservable(params: PlayerColor): Observable<PlayerData> {
        return playerDataSource.observePlayerByColor(params)
                .map { it.first() }
    }

}