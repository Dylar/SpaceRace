package de.bitb.spacerace.model.space.fields

import de.bitb.spacerace.database.converter.FieldTypeConverter
import de.bitb.spacerace.database.converter.PlayerColorConverter
import de.bitb.spacerace.model.DataObject
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import io.objectbox.BoxStore
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany


@Entity
data class FieldData(
        @Id
        override var uuid: Long = 0,
        @Convert(converter = FieldTypeConverter::class, dbType = String::class)
        var fieldType: FieldType = FieldType.UNKNOWN,
        @Convert(converter = PlayerColorConverter::class, dbType = String::class)
        var owner: PlayerColor = PlayerColor.NONE
) : DataObject() {

    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

    @JvmField
    var connections: ToMany<FieldData> = ToMany(this, FieldData_.connections)

    @JvmField
    var players: ToMany<PlayerData> = ToMany(this, FieldData_.players)

}