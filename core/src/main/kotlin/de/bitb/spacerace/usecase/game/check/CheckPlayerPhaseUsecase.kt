package de.bitb.spacerace.usecase.game.check

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.exceptions.PlayerNotInPhaseException
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ExecuteUseCase
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CheckPlayerPhaseUsecase @Inject constructor(
        private val getPlayerUsecase: GetPlayerUsecase
) : ResultUseCase<PlayerData, Pair<PlayerColor, Phase>>,
        ExecuteUseCase<Pair<PlayerColor, Phase>> {

    override fun buildUseCaseSingle(params: Pair<PlayerColor, Phase>): Single<PlayerData> =
            params.let { (playerColor, phase) ->
                getPlayerUsecase.buildUseCaseSingle(playerColor)
                        .flatMap { player ->
                            Single.create<PlayerData> {
                                if (player.phase == phase) it.onSuccess(player)
                                else it.onError(PlayerNotInPhaseException(playerColor, phase))
                            }
                        }
            }

    override fun buildUseCaseCompletable(params: Pair<PlayerColor, Phase>): Completable =
            params.let { (playerColor, phase) ->
                getPlayerUsecase.buildUseCaseSingle(playerColor)
                        .flatMapCompletable { player ->
                            Completable.create {
                                if (player.phase == phase) it.onComplete()
                                else it.onError(PlayerNotInPhaseException(playerColor, phase))
                            }
                        }
            }
}