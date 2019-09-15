package de.bitb.spacerace.database.map

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import io.objectbox.Box
import io.objectbox.query.LazyList
import io.objectbox.rx.RxQuery
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class MapRespository(
        private val fieldBox: Box<FieldData>
) : MapDataSource {

    override fun insertAll(vararg field: FieldData): Single<List<FieldData>> =
            Completable
                    .fromCallable { fieldBox.put(*field) }
                    .andThen(getAllFields(*field))

    override fun getAllFields(vararg field: FieldData): Single<List<FieldData>> =
            RxQuery.single(fieldBox.query().apply {
                if (field.isNotEmpty()) filter { field.contains(it) }
            }.build())

    override fun getField(positionData: PositionData): Single<FieldData> =
            RxQuery.single(fieldBox.query()
                    .filter { it.gamePosition.isPosition(positionData) }.build())
                    .map { it.first() }

    override fun getFieldsLazy(type: FieldType): Single<LazyList<FieldData>> =
            Single.fromCallable {
                fieldBox.query()
                        .equal(FieldData_.fieldType, type.name)
                        .build()
                        .findLazy()
            }

    override fun deleteField(vararg field: FieldData): Completable =
            Completable
                    .fromCallable { fieldBox.remove(*field) }

    override fun getPlayerField(vararg player: PlayerData): Single<List<FieldData>> =
            RxQuery.single(fieldBox.query()
                    .filter { field -> field.players.any { player.contains(it) } }
                    .build())

    override fun observeAllFields(): Observable<List<FieldData>> =
            RxQuery.observable(fieldBox.query().build())

    override fun observeByType(type: FieldType): Observable<List<FieldData>> =
            RxQuery.observable(fieldBox.query().equal(FieldData_.fieldType, type.name).build())
}