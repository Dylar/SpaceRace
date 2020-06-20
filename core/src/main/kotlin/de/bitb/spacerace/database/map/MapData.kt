package de.bitb.spacerace.database.map

import de.bitb.spacerace.database.converter.FieldTypeConverter
import de.bitb.spacerace.database.converter.PositionDataConverter
import de.bitb.spacerace.database.converter.PositionListConverter
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import io.objectbox.BoxStore
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class MapData(
        val name: String = "",
        @Id
        var uuid: Long = 0,
        @Convert(converter = PositionDataConverter::class, dbType = String::class)
        var startPosition: PositionData = PositionData()
) {

    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

    @JvmField
    var fields: ToMany<FieldConfigData> = ToMany(this, MapData_.fields)

}

val NONE_FIELD_CONFIG = FieldConfigData()

@Entity
data class FieldConfigData(
        @Id
        var uuid: Long = 0,
        @Convert(converter = FieldTypeConverter::class, dbType = String::class)
        val fieldType: FieldType = FieldType.RANDOM,
        @Convert(converter = PositionDataConverter::class, dbType = String::class)
        val gamePosition: PositionData = PositionData(),
        @Convert(converter = PositionListConverter::class, dbType = String::class)
        val connections: MutableList<PositionData> = mutableListOf(),
        @Convert(converter = PositionDataConverter::class, dbType = String::class)
        var rotateAround: PositionData? = null //TODO make me on editor
)

