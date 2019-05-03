package de.bitb.spacerace.usecase.ui

import de.bitb.spacerace.core.Dispender
import de.bitb.spacerace.database.PlayerColorDispender
import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import digital.edeka.core.usecase.UseCaseWithoutParams
import io.reactivex.Observable
import javax.inject.Inject

class PlayerChangedUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val playerColorDispender: PlayerColorDispender
) : UseCaseWithoutParams<PlayerData>(),
        Dispender<PlayerColor> by playerColorDispender {

    override fun buildUseCaseObservable(): Observable<PlayerData> {
        return publisher
                .flatMap { color ->
                    playerDataSource.getByColor(color)
                            .map { it.first() }.toObservable()
                }
    }

}