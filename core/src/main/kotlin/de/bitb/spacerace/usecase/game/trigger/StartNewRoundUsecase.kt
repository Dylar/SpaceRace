package de.bitb.spacerace.usecase.game.trigger

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.database.savegame.SaveDataSource
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.usecase.ResultUseCaseNoParams
import io.reactivex.Single
import javax.inject.Inject

class StartNewRoundUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val saveDataSource: SaveDataSource
) : ResultUseCaseNoParams<List<PlayerData>> {

    override fun buildUseCaseSingle(): Single<List<PlayerData>> =
            saveDataSource.getLoadedGame()
                    .flatMap { saveDataSource.insertAndReturnSaveData(it.apply { roundCount++ }) }
                    .map { it.players }
                    .flatMap(::resetPlayer)
                    .flatMap(::updatePlayer)

    private fun resetPlayer(player: List<PlayerData>) =
            Single.just(player.onEach { it.phase = Phase.MAIN1 })

    private fun updatePlayer(player: List<PlayerData>) =
            playerDataSource.insertAndReturn(*player.toTypedArray())

}