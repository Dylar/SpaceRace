package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.usecase.core.ExecuteUseCaseNoParams
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class StartNewRoundUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : ExecuteUseCaseNoParams {

    override fun buildUseCaseCompletable(): Completable =
            playerDataSource
                    .getAll()
                    .flatMap(::resetPlayer)
                    .flatMap(::updatePlayer)
                    .toCompletable() //TODO change that
//                    .doOnNext {
//                        playerColorDispender.publishUpdate(playerController.currentPlayerData.playerColor)
//                    }

    private fun resetPlayer(player: List<PlayerData>) =
            Single.just(player.apply { forEach { it.phase = Phase.MAIN1 } })

    private fun updatePlayer(player: List<PlayerData>) =
            playerDataSource.insertAll(*player.toTypedArray())

}