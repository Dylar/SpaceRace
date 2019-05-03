package de.bitb.spacerace.usecase

import de.bitb.spacerace.database.PlayerColorDispender
import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class LoadPlayerUsecase @Inject constructor(
        private val playerColorDispender: PlayerColorDispender,
        private val playerDataSource: PlayerDataSource
) : UseCase<List<PlayerData>, List<PlayerColor>>() {

    override fun buildUseCaseObservable(params: List<PlayerColor>): Observable<List<PlayerData>> {
        return playerDataSource
                .getByColor(*params.toTypedArray())
                .map { insertNewPlayer(it, params) }
                .flatMap { it }
                .map { pushCurrentPlayer(it) }
                .toObservable()
    }

    private fun insertNewPlayer(list: List<PlayerData>, params: List<PlayerColor>): Single<List<PlayerData>> {
        return playerDataSource
                .insertAll(*params.map { color ->
                    list.find { it.playerColor == color }
                            ?.let { it }
                            ?: PlayerData(playerColor = color)
                }.toTypedArray())
    }

    private fun pushCurrentPlayer(list: List<PlayerData>): List<PlayerData> {
        return list.also {
            if (list.isNotEmpty()) {
                playerColorDispender.publisher.onNext(it.last().playerColor)
            }
        }
    }
}
