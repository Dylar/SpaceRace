package de.bitb.spacerace.model.space.fields

import de.bitb.spacerace.database.converter.FieldTypeConverter
import de.bitb.spacerace.database.converter.PlayerColorConverter
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class FieldData(
        @Id
        var uuid: Long = 0,
        @Convert(converter = FieldTypeConverter::class, dbType = String::class)
        var fieldType: FieldType = FieldType.UNKNOWN,
        var position: ToOne<PositionData>,
        @Convert(converter = PlayerColorConverter::class, dbType = String::class)
        var owner: PlayerColor = PlayerColor.NONE)