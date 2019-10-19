package de.bitb.spacerace.database.savegame

import io.objectbox.Box
import io.objectbox.rx.RxQuery
import io.reactivex.Completable
import io.reactivex.Single

class SaveRespository(
        private val saveBox: Box<SaveData>
) : SaveDataSource {

    override fun loadGame(saveData: SaveData): Single<SaveData> =
            Single.create<SaveData> { emitter ->
                val allSaveData =
                        saveBox.all.onEach { it.loaded = false }
                saveBox.put(*allSaveData.toTypedArray())

                saveData.loaded = true
                saveBox.put(saveData)
                emitter.onSuccess(saveBox.get(saveData.uuid))
            }

    override fun insertAndReturnSaveData(mapData: SaveData): Single<SaveData> =
            insertSaveData(mapData)
                    .andThen(Single.fromCallable { saveBox.all.last() })

    override fun insertSaveData(mapData: SaveData): Completable =
            Completable.fromAction {
                saveBox.put(mapData)
            }

    override fun getRXLoadedGame(): Single<SaveData> =
            RxQuery.single(saveBox.query()
                    .equal(SaveData_.loaded, true).build())
                    .map { it.first() }

    override fun getLoadedGame(): SaveData =
            saveBox.query().equal(SaveData_.loaded, true).build().find().first()

    override fun deleteSaveGame(saveData: SaveData): Completable =
            Completable.fromAction {
                saveBox.query()
                        .equal(SaveData_.name, saveData.name).build()
                        .remove()
            }
}