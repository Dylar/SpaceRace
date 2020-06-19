package de.bitb.spacerace.database.savegame

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single


interface SaveDataSource {

    fun insertRXSaveData(vararg saveData: SaveData): Completable
    fun insertAndReturnRXSaveData(vararg saveData: SaveData): Single<List<SaveData>>

    fun deleteRXSaveGame(saveData: SaveData): Completable

    fun getDBLoadedGame(): SaveData
    fun getRXAllGames(): Single<List<SaveData>>
    fun getRXLoadedGame(): Single<SaveData>

}
