package de.bitb.spacerace.database.items

import io.objectbox.Box
import io.objectbox.kotlin.inValues
import io.objectbox.rx.RxQuery
import io.reactivex.Completable
import io.reactivex.Single

class ItemRespository(
        private val itemBox: Box<ItemData>
) : ItemDataSource {

    override fun insertAll(vararg items: ItemData): Single<List<ItemData>> =
            Completable
                    .fromCallable { itemBox.put(*items) }
                    .andThen(getItems(*items.map { it.id }.toLongArray()))

    override fun deleteItems(vararg items: ItemData): Completable =
            Completable
                    .fromCallable { itemBox.remove(*items) }

    override fun getItems(vararg items: Long): Single<List<ItemData>> =
            RxQuery.single(itemBox.query()
                    .inValues(ItemData_.id, items).build())

}