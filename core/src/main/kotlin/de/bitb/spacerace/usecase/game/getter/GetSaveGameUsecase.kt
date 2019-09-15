package de.bitb.spacerace.usecase.game.getter

import de.bitb.spacerace.database.savegame.SaveGame
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.usecase.ResultUseCaseNoParams
import io.reactivex.Single
import javax.inject.Inject

class GetSaveGameUsecase @Inject constructor(
        private val mapDataSource: MapDataSource
) : ResultUseCaseNoParams<SaveGame> {

    override fun buildUseCaseSingle(): Single<SaveGame> {
        return mapDataSource
                .getSaveGame()
    }
}