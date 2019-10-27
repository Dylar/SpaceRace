package de.bitb.spacerace.database.map

import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import io.reactivex.Single

interface MapDataSource {

    fun insertDBMaps(vararg maps: MapData)
    fun getDBAllMaps(): List<MapData>
    fun getDBMaps(vararg name: String): List<MapData>

    fun insertDBField(vararg maps: FieldData)
    fun getDBFields(vararg ids: Long): List<FieldData>
    fun getRXAllFields(vararg field: FieldData): Single<List<FieldData>>
    fun getRXFieldByPosition(vararg positionData: PositionData): Single<List<FieldData>>
    fun getRXFieldByType(type: FieldType): Single<List<FieldData>>


}
