package de.bitb.spacerace.usecase.game.check

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.core.exceptions.WrongPhaseException
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.ExecuteUseCase
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.game.getter.GetPlayerUsecase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CheckPlayerPhaseUsecase @Inject constructor(
        private val getPlayerUsecase: GetPlayerUsecase
) : ResultUseCase<PlayerData, CheckPlayerConfig>,
        ExecuteUseCase<CheckPlayerConfig> {

    override fun buildUseCaseSingle(params: CheckPlayerConfig): Single<PlayerData> =
            params.let { (playerColor, phase) ->
                getPlayerUsecase.buildUseCaseSingle(playerColor)
                        .flatMap { player ->
                            Single.create<PlayerData> { emitter ->
                                if (phase.any { it == player.phase }) emitter.onSuccess(player)
                                else emitter.onError(WrongPhaseException(playerColor, phase))
                            }
                        }
            }

    override fun buildUseCaseCompletable(params: CheckPlayerConfig): Completable =
            params.let { (playerColor, phase) ->
                getPlayerUsecase.buildUseCaseSingle(playerColor)
                        .flatMapCompletable { player ->
                            Completable.create { emitter ->
                                if (phase.any { it == player.phase }) emitter.onComplete()
                                else emitter.onError(WrongPhaseException(playerColor, phase))
                            }
                        }
            }
}

data class CheckPlayerConfig(
        val player: PlayerColor,
        val phaseToCheck: Set<Phase>
)