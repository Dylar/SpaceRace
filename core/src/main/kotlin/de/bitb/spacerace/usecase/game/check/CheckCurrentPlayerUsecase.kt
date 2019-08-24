package de.bitb.spacerace.usecase.game.check

import de.bitb.spacerace.core.PlayerColorDispender
import de.bitb.spacerace.exceptions.NotCurrentPlayerException
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ExecuteUseCase
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
import io.reactivex.Completable
import javax.inject.Inject

class CheckCurrentPlayerUsecase @Inject constructor(
        private val playerColorDispender: PlayerColorDispender
) : ExecuteUseCase<PlayerColor> {

    override fun buildUseCaseCompletable(params: PlayerColor): Completable =
            Completable.create {
                val currentPlayer = playerColorDispender.publisher.value
                if (params == currentPlayer) it.onComplete()
                else it.onError(NotCurrentPlayerException(params))
            }
}