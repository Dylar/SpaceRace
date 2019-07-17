package de.bitb.spacerace.usecase.init

import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.database.player.PlayerColorDispender
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.core.ResultUseCase
import io.reactivex.Single
import javax.inject.Inject

class LoadPlayerUsecase @Inject constructor(
        private val playerController: PlayerController,
        private val playerColorDispender: PlayerColorDispender,
        private val playerDataSource: PlayerDataSource
) : ResultUseCase<List<PlayerData>, List<PlayerColor>> {

    override fun buildUseCaseSingle(params: List<PlayerColor>): Single<List<PlayerData>> =
            playerDataSource
//                .insertAll(*params.map { PlayerData(playerData = it) }.toTypedArray())
                    .getByColor(*params.toTypedArray())
                    .map {
                        insertNewPlayer(it, params)
                    }
                    .flatMap { it }
                    .doAfterSuccess {
                        pushCurrentPlayer(it)
                    }

    private fun insertNewPlayer(list: List<PlayerData>, params: List<PlayerColor>): Single<List<PlayerData>> {
        return playerDataSource
                .insertAll(*params
                        .map { color ->
                            list.find { it.playerColor == color }
                                    ?.let { it }
                                    ?: PlayerData(playerColor = color)
                        }.toTypedArray()
                )
    }

    private fun pushCurrentPlayer(list: List<PlayerData>) {
        if (list.isNotEmpty()) {
            playerController.currentPlayer.playerColor
                    .let {
                        when (it) {
                            PlayerColor.NONE -> list.first().playerColor
                            else -> it
                        }
                    }.also {
                        playerColorDispender.publisher.onNext(it)
                    }

        }
    }
}
