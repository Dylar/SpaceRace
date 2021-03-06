package de.bitb.spacerace.usecase.game.trigger

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.database.savegame.SaveDataSource
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.usecase.ResultUseCaseNoParams
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StartNewRoundUsecase @Inject constructor(
        private val playerDataSource: PlayerDataSource,
        private val saveDataSource: SaveDataSource
) : ResultUseCaseNoParams<List<PlayerData>> {

    override fun buildUseCaseSingle(): Single<List<PlayerData>> =
            saveDataSource.getRXLoadedGame()
                    .map { it.apply { roundCount++ } }
                    .flatMap { saveDataSource.insertAndReturnRXSaveData(it) }
                    .map { it.first().players }
                    .map(::resetPlayer)
                    .flatMap(::updatePlayer)

    private fun resetPlayer(player: List<PlayerData>) =
            player.onEach {
                it.clearTurn()
                it.phase = Phase.MAIN1
            }

    private fun updatePlayer(player: List<PlayerData>) =
            playerDataSource.insertAndReturnRXPlayer(*player.toTypedArray())

}