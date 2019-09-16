package de.bitb.spacerace.database.savegame

import io.objectbox.Box
import io.reactivex.Completable
import io.reactivex.Single

class SaveRespository(
        private val mapBox: Box<SaveData>
) : SaveDataSource {
    override fun insertAndReturnSaveData(mapData: SaveData): Single<SaveData> =
            insertSaveData(mapData)
                    .andThen(Single.fromCallable { mapBox.all.last() })

    override fun insertSaveData(mapData: SaveData): Completable =
            Completable.fromAction {
                mapBox.put(mapData)
            }

    override fun getSaveGame(): Single<SaveData> = Single.fromCallable { mapBox.all.first() }

    override fun deleteSaveGame(saveData: SaveData): Completable =
            Completable.fromAction {
                mapBox.query()
                        .equal(SaveData_.name, saveData.name).build()
                        .remove()
            }
}