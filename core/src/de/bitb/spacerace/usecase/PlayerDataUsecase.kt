package de.bitb.spacerace.usecase

import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import io.reactivex.Observable
import javax.inject.Inject

class PlayerDataUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : UseCase<PlayerData, PlayerColor>() {

    override fun buildUseCaseObservable(params: PlayerColor): Observable<PlayerData> {
        return playerDataSource.observeByColor(params)
                .map { it.first() }
//                .onErrorReturn { PlayerData() }
    }
}