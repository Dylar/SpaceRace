package de.bitb.spacerace.database.items

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import io.objectbox.query.LazyList
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ItemDataSource {

    fun insertAll(vararg field: ItemData): Single<List<ItemData>>

    fun deleteItems(vararg field: ItemData): Completable

    fun getAllItems(vararg field: ItemData): Single<List<ItemData>>

    fun getPlayerField(vararg player: PlayerData): Single<List<ItemData>>

    fun observeAllFields(): Observable<List<ItemData>>

    fun observeByType(type: FieldType): Observable<List<ItemData>>

}
