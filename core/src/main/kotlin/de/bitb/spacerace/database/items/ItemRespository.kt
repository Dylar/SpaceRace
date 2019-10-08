package de.bitb.spacerace.database.items

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import io.objectbox.Box
import io.objectbox.query.LazyList
import io.objectbox.rx.RxQuery
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class ItemRespository(
        private val itemBox: Box<ItemData>
) : ItemDataSource {

    override fun insertAll(vararg items: ItemData): Single<List<ItemData>> =
            Completable
                    .fromCallable { itemBox.put(*items) }
                    .andThen(getAllItems(*items))

    override fun getAllItems(vararg items: ItemData): Single<List<ItemData>> =
            RxQuery.single(itemBox.query().apply {
                if (items.isNotEmpty()) filter { items.contains(it) }
            }.build())

//    override fun getField(positionData: PositionData): Single<ItemData> =
//            RxQuery.single(itemBox.query()
//                    .filter { it.gamePosition.isPosition(positionData) }.build())
//                    .map { it.first() }
//
//    override fun getFieldsLazy(type: FieldType): Single<LazyList<ItemData>> =
//            Single.fromCallable {
//                itemBox.query()
//                        .equal(ItemData_.fieldType, type.name)
//                        .build()
//                        .findLazy()
//            }

    override fun deleteItems(vararg field: ItemData): Completable =
            Completable
                    .fromCallable { itemBox.remove(*field) }

    override fun getPlayerField(vararg player: PlayerData): Single<List<ItemData>> =
            RxQuery.single(itemBox.query()
//                    .filter { field -> field.players.any { player.contains(it) } }
                    .build())

    override fun observeAllFields(): Observable<List<ItemData>> =
            RxQuery.observable(itemBox.query().build())

    override fun observeByType(type: FieldType): Observable<List<ItemData>> =
            RxQuery.observable(itemBox.query()
//                    .equal(ItemData_.fieldType, type.name)
                    .build())
}