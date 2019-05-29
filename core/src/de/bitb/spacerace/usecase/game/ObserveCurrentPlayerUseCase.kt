package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.core.Dispender
import de.bitb.spacerace.database.PlayerColorDispender
import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.usecase.UseCaseWithoutParams
import io.reactivex.Observable
import javax.inject.Inject

class ObserveCurrentPlayerUseCase @Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val playerColorDispender: PlayerColorDispender
) : UseCaseWithoutParams<PlayerData>(),
        Dispender<PlayerColor> by playerColorDispender {

    override fun buildUseCaseObservable(): Observable<PlayerData> {
        return publisher
                .flatMap {
                    playerDataSource.observeByColor(it)
                }
                .map { it.first() }
    }

}