package de.bitb.spacerace.usecase

import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerData
import io.reactivex.Observable
import javax.inject.Inject

class LoadPlayerUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : UseCase<PlayerData, PlayerData>() {


    override fun buildUseCaseObservable(params: PlayerData): Observable<PlayerData> {
        return playerDataSource
                .insertAll(params)
                .map {
                    it.first()
                }
                .toObservable()


    }

}