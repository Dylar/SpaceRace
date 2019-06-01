package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.config.WIN_AMOUNT
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Observable
import javax.inject.Inject

class AnnounceWinnerUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : UseCase<List<PlayerData>, Long>() {

    override fun buildUseCaseObservable(params: Long): Observable<List<PlayerData>> {
        return playerDataSource
                .observeAllObserver()
                .map {all ->
                    all.filter {
                        it.victories == WIN_AMOUNT
                    }
                }
    }

}