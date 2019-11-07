package de.bitb.spacerace.database.items

import io.reactivex.Completable
import io.reactivex.Single

interface ItemDataSource {

    fun insertRXItems(vararg items: ItemData): Single<List<ItemData>>

    fun deleteRXItems(vararg items: ItemData): Completable

    fun getAllDBItems(): List<ItemData>
    fun getDBItems(vararg items: Long): List<ItemData>
    fun getRXItems(vararg items: Long): Single<List<ItemData>>

}
