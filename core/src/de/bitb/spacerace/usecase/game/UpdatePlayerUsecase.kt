package de.bitb.spacerace.usecase.game

import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.UseCase
import io.reactivex.Observable
import javax.inject.Inject

class UpdatePlayerUsecase @Inject constructor(
        private val playerController: PlayerController,
        private val playerDataSource: PlayerDataSource
) : UseCase<List<PlayerData>, List<PlayerData>>() {

    override fun buildUseCaseObservable(params: List<PlayerData>): Observable<List<PlayerData>> {
        return playerDataSource
                .insertAll(*params.toTypedArray())
                .doOnSuccess {
                    playerController.updatePlayer(it) //TODO necessary?
                }
                .toObservable()
    }
}