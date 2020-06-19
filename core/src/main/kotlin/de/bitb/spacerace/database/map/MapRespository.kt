package de.bitb.spacerace.database.map

import de.bitb.spacerace.database.items.MovableItem
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import io.objectbox.Box
import io.objectbox.kotlin.inValues
import io.objectbox.rx3.RxQuery
import io.reactivex.rxjava3.core.Single

class MapRespository(
        private val fieldBox: Box<FieldData>,
        private val mapBox: Box<MapData>
) : MapDataSource {

    override fun insertDBMaps(vararg maps: MapData) {
        mapBox.put(*maps)
    }

    override fun getDBAllMaps(): List<MapData> = mapBox.all

    override fun getDBMaps(vararg name: String): List<MapData> =
            mapBox.query()
                    .inValues(MapData_.name, arrayOf(*name))
                    .build().find()

    override fun getRXMaps(vararg name: String): Single<List<MapData>> =
            RxQuery.single(mapBox.query()
                    .inValues(MapData_.name, arrayOf(*name))
                    .build())

    override fun insertDBField(vararg maps: FieldData) {
        fieldBox.put(*maps)
    }

    override fun getDBFields(vararg ids: Long): List<FieldData> =
            fieldBox.query()
                    .inValues(FieldData_.uuid, longArrayOf(*ids))
                    .build().find()

    override fun getRXAllFields(vararg field: FieldData): Single<List<FieldData>> =
            RxQuery.single(fieldBox.query().apply {
                if (field.isNotEmpty()) filter { field.contains(it) }
            }.build())

    override fun getRXFieldByPosition(vararg positionData: PositionData) =
            RxQuery.single(fieldBox.query()
                    .filter { field -> positionData.any { it.isPosition(field.gamePosition) } }.build())

    override fun getRXFieldByType(type: FieldType): Single<List<FieldData>> =
            RxQuery.single(fieldBox.query()
                    .equal(FieldData_.fieldType, type.name)
                    .build())

    override fun getRXFieldWithMovableItems(): Single<List<FieldData>> =
            RxQuery.single(fieldBox.query()
                    .filter { field -> field.disposedItems.any { it.itemInfo is MovableItem } }.build())
}