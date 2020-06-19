package de.bitb.spacerace.database.savegame

import io.objectbox.Box
import io.objectbox.kotlin.inValues
import io.objectbox.rx3.RxQuery
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class SaveRespository(
        private val saveBox: Box<SaveData>
) : SaveDataSource {

    override fun getRXAllGames(): Single<List<SaveData>> = Single.fromCallable { saveBox.all }

    override fun insertAndReturnRXSaveData(vararg saveData: SaveData): Single<List<SaveData>> =
            insertRXSaveData(*saveData)
                    .andThen(Single.fromCallable {
                        val ids = saveData.map { it.uuid }.toLongArray()
                        saveBox.query()
                                .inValues(SaveData_.uuid, ids)
                                .build().find()
                    })

    override fun insertRXSaveData(vararg saveData: SaveData): Completable =
            Completable.fromAction { saveBox.put(*saveData) }

    override fun getRXLoadedGame(): Single<SaveData> =
            RxQuery.single(saveBox.query()
                    .equal(SaveData_.loaded, true).build())
                    .map { it.first() }

    override fun getDBLoadedGame(): SaveData =
            saveBox.query()
                    .equal(SaveData_.loaded, true).build().find()
                    .first()

    override fun deleteRXSaveGame(saveData: SaveData): Completable =
            Completable.fromAction {
                saveBox.query()
                        .equal(SaveData_.name, saveData.name).build()
                        .remove()
            }
}