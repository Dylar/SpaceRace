package de.bitb.spacerace.usecase

import de.bitb.spacerace.database.PlayerColorDispender
import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerData
import io.reactivex.Observable
import javax.inject.Inject

class LoadPlayerUsecase @Inject constructor(
        private val playerColorDispender: PlayerColorDispender,
        private val playerDataSource: PlayerDataSource
) : UseCase<List<PlayerData>, List<PlayerData>>() {

    override fun buildUseCaseObservable(params: List<PlayerData>): Observable<List<PlayerData>> {
        return playerDataSource
                .insertAll(*params.toTypedArray())
                .map { list ->
                    list.also {
                        playerColorDispender.pusher.onNext(it.first().playerColor)
                    }
                }
                .toObservable()
    }

}