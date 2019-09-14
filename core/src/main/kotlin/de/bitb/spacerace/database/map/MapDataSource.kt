package de.bitb.spacerace.database.map

import de.bitb.spacerace.database.SaveGame
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.objecthandling.PositionData
import io.objectbox.query.LazyList
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MapDataSource {

    fun insertMap(mapData: SaveGame): Completable

    fun insertAll(vararg field: FieldData): Single<List<FieldData>>

    fun deleteField(vararg field: FieldData): Completable

    fun deleteMap(): Completable

    fun getMap(): Single<SaveGame>

    fun getAllFields(vararg field: FieldData): Single<List<FieldData>>

    fun getField(positionData: PositionData): Single<FieldData>

    fun getFieldsLazy(type: FieldType): Single<LazyList<FieldData>>

    fun getPlayerField(vararg player: PlayerData): Single<List<FieldData>>

    fun observeAllFields(): Observable<List<FieldData>>

    fun observeByType(type: FieldType): Observable<List<FieldData>>

//    fun getDBById(vararg id: Long): List<FieldData>
}
