package de.bitb.spacerace.database.map

import de.bitb.spacerace.database.converter.FieldTypeConverter
import de.bitb.spacerace.database.converter.PositionDataConverter
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import io.objectbox.BoxStore
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne


val NONE_FIELD_DATA = FieldData()

@Entity
data class FieldData(
        @Id
        var uuid: Long = 0,
        @Convert(converter = FieldTypeConverter::class, dbType = String::class)
        var fieldType: FieldType = FieldType.UNKNOWN,

        @Convert(converter = PositionDataConverter::class, dbType = String::class)
        val gamePosition: PositionData = PositionData()
) {

    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

    @JvmField
    var connections: ToMany<FieldData> = ToMany(this, FieldData_.connections)

    @JvmField
    @Backlink(to = "positionField")
    var players: ToMany<PlayerData> = ToMany(this, FieldData_.players)

    @JvmField
    var owner: ToOne<PlayerData> = ToOne(this, FieldData_.owner)

}

infix fun FieldData.isConnectedTo(fieldData: FieldData) = connections.any { it.gamePosition.isPosition(fieldData.gamePosition) }