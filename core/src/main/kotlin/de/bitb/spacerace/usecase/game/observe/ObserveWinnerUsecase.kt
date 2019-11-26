package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.StreamUseCase
import io.reactivex.Observable
import javax.inject.Inject

class ObserveWinnerUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : StreamUseCase<PlayerData, Long> {

    override fun buildUseCaseObservable(params: Long): Observable<PlayerData> =
            playerDataSource
                    .observeAllPlayer()
                    .flatMapIterable { it }
                    .filter { it.victories == params }

}