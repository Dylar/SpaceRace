package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.usecase.UseCaseWithoutParams
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class StartNewRoundUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : UseCaseWithoutParams<Boolean>() {

    override fun buildUseCaseObservable(): Observable<Boolean> =
            playerDataSource
                    .getAll()
                    .flatMap(::resetPlayer)
                    .flatMap(::updatePlayer)
                    .flatMapObservable { Observable.just(it.isNotEmpty()) }
//                    .doOnNext {
//                        playerColorDispender.publishUpdate(playerController.currentPlayerData.playerColor)
//                    }

    private fun resetPlayer(player: List<PlayerData>) =
            Single.just(player.apply { forEach { it.phase = Phase.MAIN1 } })

    private fun updatePlayer(player: List<PlayerData>) =
            playerDataSource.insertAll(*player.toTypedArray())

}