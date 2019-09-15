package de.bitb.spacerace.database.savegame

import de.bitb.spacerace.database.SaveGame_
import io.objectbox.Box
import io.reactivex.Completable
import io.reactivex.Single

class SaveRespository(
        private val mapBox: Box<SaveGame>
) : SaveDataSource {

    override fun insertSaveGame(mapData: SaveGame): Completable =
            Completable.fromAction {
                mapBox.put(mapData)
            }

    override fun getSaveGame(): Single<SaveGame> = Single.fromCallable { mapBox.all.first() }

    override fun deleteSaveGame(saveGame: SaveGame): Completable =
            Completable.fromAction {
                mapBox.query()
                        .equal(SaveGame_.name, saveGame.name).build()
                        .remove()
            }
}