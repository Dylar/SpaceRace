package de.bitb.spacerace.usecase.game.trigger

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.usecase.ResultUseCaseNoParams
import io.reactivex.Single
import javax.inject.Inject

class StartNewRoundUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : ResultUseCaseNoParams<List<PlayerData>> {

    override fun buildUseCaseSingle(): Single<List<PlayerData>> =
            playerDataSource
                    .getAll()
                    .flatMap(::resetPlayer)
                    .flatMap(::updatePlayer)
//                    .doOnNext {
//                        playerColorDispender.publishUpdate(playerController.currentPlayerData.playerColor)
//                    }

    private fun resetPlayer(player: List<PlayerData>) =
            Single.just(player.apply { forEach { it.phase = Phase.MAIN1 } })

    private fun updatePlayer(player: List<PlayerData>) =
            playerDataSource.insertAllReturnAll(*player.toTypedArray())

}