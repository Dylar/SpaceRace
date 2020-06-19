package de.bitb.spacerace.database.items

import io.objectbox.Box
import io.objectbox.kotlin.inValues
import io.objectbox.rx3.RxQuery
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ItemRespository(
        private val itemBox: Box<ItemData>
) : ItemDataSource {

    override fun insertRXItems(vararg items: ItemData): Single<List<ItemData>> =
            Completable.fromCallable { itemBox.put(*items) }
                    .andThen(getRXItems(*items.map { it.id }.toLongArray()))

    override fun deleteRXItems(vararg items: ItemData): Completable =
            Completable.fromCallable { itemBox.remove(*items) }

    override fun getAllDBItems(): List<ItemData> = itemBox.all
    override fun getDBItems(vararg items: Long): List<ItemData> = itemBox.query().inValues(ItemData_.id, items).build().find()
    override fun getRXItems(vararg items: Long): Single<List<ItemData>> =
            RxQuery.single(itemBox.query()
                    .inValues(ItemData_.id, items).build())

}