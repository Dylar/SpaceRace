package de.bitb.spacerace.usecase.game.getter

import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.database.savegame.SaveDataSource
import de.bitb.spacerace.usecase.ResultUseCaseNoParams
import io.reactivex.Single
import javax.inject.Inject

class GetSaveGameUsecase @Inject constructor(
        private val saveDataSource: SaveDataSource
) : ResultUseCaseNoParams<SaveData> {

    override fun buildUseCaseSingle(): Single<SaveData> {
        return saveDataSource
                .getSaveGame()
    }
}