package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class NextPhaseUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : UseCase<Boolean, Pair<PlayerData, (PlayerData) -> PlayerData>>() {

    override fun buildUseCaseObservable(params: Pair<PlayerData, (PlayerData) -> PlayerData>): Observable<Boolean> =
            params.let { (playerData, doPhase) ->
                Single.create<PlayerData> { emitter -> emitter.onSuccess(doPhase(playerData)) }
                        .flatMap { intoDb -> playerDataSource.insertAll(intoDb) }
                        .map { true }
                        .toObservable()
            }


}