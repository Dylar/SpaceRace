package de.bitb.spacerace.usecase

import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerData
import io.reactivex.Observable
import javax.inject.Inject

class UpdatePlayerUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : UseCase<List<PlayerData>, List<PlayerData>>() {

    override fun buildUseCaseObservable(params: List<PlayerData>): Observable<List<PlayerData>> {
        return playerDataSource
                .insertAll(*params.toTypedArray())
                .toObservable()
    }
}