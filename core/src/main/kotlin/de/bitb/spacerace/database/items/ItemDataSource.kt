package de.bitb.spacerace.database.items

import io.reactivex.Completable
import io.reactivex.Single

interface ItemDataSource {

    fun insertAll(vararg items: ItemData): Single<List<ItemData>>

    fun deleteItems(vararg items: ItemData): Completable

    fun getItems(vararg items: Long): Single<List<ItemData>>

}
