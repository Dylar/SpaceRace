package de.bitb.spacerace.database.savegame

import io.reactivex.Completable
import io.reactivex.Single

interface SaveDataSource {

    fun insertSaveData(saveData: SaveData): Completable
    fun insertAndReturnSaveData(saveData: SaveData): Single<SaveData>

    fun deleteSaveGame(saveData: SaveData): Completable

    fun getLoadedGame(): SaveData
    fun getRXLoadedGame(): Single<SaveData>
    fun loadGame(saveData: SaveData): Single<SaveData>

}
