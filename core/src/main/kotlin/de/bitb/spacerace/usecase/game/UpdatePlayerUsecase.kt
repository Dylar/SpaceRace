package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.core.ExecuteUseCase
import io.reactivex.Completable
import javax.inject.Inject

class UpdatePlayerUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : ExecuteUseCase<List<PlayerData>> {

    override fun buildUseCaseCompletable(params: List<PlayerData>): Completable {
        return playerDataSource
                .insertAll(*params.toTypedArray())
                .toCompletable() //TODO change that
    }
}