package de.bitb.spacerace.database.map

import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.database.player.PlayerData
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MapDataSource {

    fun insertMap(mapData:MapData): Completable

    fun insertAll(vararg field: FieldData): Single<List<FieldData>>

    fun delete(vararg field: FieldData): Completable

    fun getAllFields(vararg field: FieldData): Single<List<FieldData>>

    fun getPlayerField(vararg player: PlayerData): Single<List<FieldData>>

    fun observeAllFields(): Observable<List<FieldData>>

    fun observeByType(type: FieldType): Observable<List<FieldData>>

}
