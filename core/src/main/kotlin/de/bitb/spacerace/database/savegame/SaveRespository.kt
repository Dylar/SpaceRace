package de.bitb.spacerace.database.savegame

import de.bitb.spacerace.database.SaveGame_
import io.objectbox.Box
import io.reactivex.Completable
import io.reactivex.Single

class SaveRespository(
        private val mapBox: Box<SaveData>
) : SaveDataSource {

    override fun insertSaveGame(mapData: SaveData): Completable =
            Completable.fromAction {
                mapBox.put(mapData)
            }

    override fun getSaveGame(): Single<SaveData> = Single.fromCallable { mapBox.all.first() }

    override fun deleteSaveGame(saveData: SaveData): Completable =
            Completable.fromAction {
                mapBox.query()
                        .equal(SaveGame_.name, saveData.name).build()
                        .remove()
            }
}