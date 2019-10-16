package de.bitb.spacerace.database.map

import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import io.reactivex.Single

interface MapDataSource {

    fun getAllMaps(): List<MapData>

    fun getDBMaps(vararg name: String): List<MapData>

    fun getAllFields(vararg field: FieldData): Single<List<FieldData>>

    fun getFieldByPosition(vararg positionData: PositionData): Single<List<FieldData>>

    fun getFieldByType(type: FieldType): Single<List<FieldData>>

    fun insertMaps(vararg maps: MapData)

}
