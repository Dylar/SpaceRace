package de.bitb.spacerace.database.savegame

import io.reactivex.Completable
import io.reactivex.Single

interface SaveDataSource {

    fun insertSaveGame(mapData: SaveData): Completable

    fun deleteSaveGame(saveData: SaveData): Completable

    fun getSaveGame(): Single<SaveData>
}
