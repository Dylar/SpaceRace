package de.bitb.spacerace.usecase.game.check

import de.bitb.spacerace.core.exceptions.WrongPhaseException
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.ExecuteUseCase
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CheckPlayerPhaseUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<PlayerData, CheckPlayerConfig>,
        ExecuteUseCase<CheckPlayerConfig> {

    override fun buildUseCaseSingle(params: CheckPlayerConfig): Single<PlayerData> =
            params.let { (playerColor, phase) ->
                playerDataSource.getRXPlayerByColor(playerColor)
                        .map { it.first() }
                        .flatMap { player ->
                            Single.create<PlayerData> { emitter ->
                                if (phase.any { it == player.phase }) emitter.onSuccess(player)
                                else emitter.onError(WrongPhaseException(playerColor, phase))
                            }
                        }
            }

    override fun buildUseCaseCompletable(params: CheckPlayerConfig): Completable =
            params.let { (playerColor, phase) ->
                playerDataSource.getRXPlayerByColor(playerColor)
                        .map { it.first() }
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