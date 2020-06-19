package de.bitb.spacerace.usecase.game.getter

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetPlayerUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<PlayerData, PlayerColor> {

    override fun buildUseCaseSingle(params: PlayerColor): Single<PlayerData> {
        return playerDataSource
                .getRXPlayerByColor(params)
                .map { it.first() }
    }
}