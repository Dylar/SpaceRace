package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.StreamUseCase
import io.reactivex.Observable
import javax.inject.Inject

class ObserveWinnerUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : StreamUseCase<PlayerData, Long> {

    override fun buildUseCaseFlowable(params: Long): Observable<PlayerData> =
            playerDataSource
                    .observeAllObserver()
                    .flatMapIterable { it }
                    .filter { it.victories == params }

}