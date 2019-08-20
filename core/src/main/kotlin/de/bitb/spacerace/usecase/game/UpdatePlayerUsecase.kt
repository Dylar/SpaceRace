package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Single
import javax.inject.Inject

class UpdatePlayerUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<List<PlayerData>, List<PlayerData>> {

    override fun buildUseCaseSingle(params: List<PlayerData>): Single<List<PlayerData>> {
        return playerDataSource
                .insertAllReturnAll(*params.toTypedArray())
    }
}