package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Observable
import javax.inject.Inject

class AnnounceWinnerUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : UseCase<PlayerData, Long>() {

    override fun buildUseCaseObservable(params: Long): Observable<PlayerData> {
        return playerDataSource
                .observeAllObserver()
                .map { all ->
                    all.find {
                        it.victories == params
                    }
                }
    }

}