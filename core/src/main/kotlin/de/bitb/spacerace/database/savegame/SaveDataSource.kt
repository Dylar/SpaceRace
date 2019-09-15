package de.bitb.spacerace.database.savegame

import io.reactivex.Completable
import io.reactivex.Single

interface SaveDataSource {

    fun insertSaveGame(mapData: SaveGame): Completable

    fun deleteSaveGame(saveGame: SaveGame): Completable

    fun getSaveGame(): Single<SaveGame>
}
