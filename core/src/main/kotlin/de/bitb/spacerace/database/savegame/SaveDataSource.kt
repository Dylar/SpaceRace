package de.bitb.spacerace.database.savegame

import io.reactivex.Completable
import io.reactivex.Single

interface SaveDataSource {

    fun insertSaveData(mapData: SaveData): Completable
    fun insertAndReturnSaveData(mapData: SaveData): Single<SaveData>

    fun deleteSaveGame(saveData: SaveData): Completable

    fun getSaveGame(): Single<SaveData>
    fun loadGame(saveData: SaveData): Single<SaveData>

}
