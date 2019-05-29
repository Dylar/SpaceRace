package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Observable
import javax.inject.Inject

class ObservePlayerUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : UseCase<List<PlayerData>, PlayerColor>() {

    override fun buildUseCaseObservable(params: PlayerColor): Observable<List<PlayerData>> {
            return playerDataSource
                    .observeByColor(params)
    }
}