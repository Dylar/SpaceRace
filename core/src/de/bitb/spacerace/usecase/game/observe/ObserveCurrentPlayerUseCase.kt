package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.Logger
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.Dispender
import de.bitb.spacerace.database.player.PlayerColorDispender
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.UseCaseWithoutParams
import io.reactivex.Observable

class ObserveCurrentPlayerUseCase(
        private val playerDataSource: PlayerDataSource,
        private val playerColorDispender: PlayerColorDispender
) : UseCaseWithoutParams<PlayerData>(),
        Dispender<PlayerColor> by playerColorDispender {

    override fun buildUseCaseObservable(): Observable<PlayerData> {
        return publisher
                .doOnNext { Logger.println("CURRENT PLAYER1: $it") }
                .flatMap { playerDataSource.observeByColor(it) }
                .map { it.first() }
                .doOnNext { Logger.println("CURRENT PLAYER2: $it") }
    }

}