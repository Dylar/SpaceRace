package de.bitb.spacerace.usecase.game.check

import de.bitb.spacerace.core.exceptions.NotCurrentPlayerException
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.ExecuteUseCase
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.dispender.PlayerColorDispenser
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CheckCurrentPlayerUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val playerColorDispenser: PlayerColorDispenser
) : ExecuteUseCase<PlayerColor>,
        ResultUseCase<PlayerData, PlayerColor> {

    override fun buildUseCaseCompletable(params: PlayerColor): Completable =
            Completable.create { emitter ->
                val currentPlayer = playerColorDispenser.publisher.value
                if (params == currentPlayer) emitter.onComplete()
                else emitter.onError(NotCurrentPlayerException(params))
            }

    override fun buildUseCaseSingle(params: PlayerColor): Single<PlayerData> =
            buildUseCaseCompletable(params)
                    .andThen(playerDataSource.getRXPlayerByColor(params))
                    .map { it.first() }

}