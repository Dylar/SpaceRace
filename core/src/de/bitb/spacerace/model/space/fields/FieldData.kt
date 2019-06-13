package de.bitb.spacerace.model.space.fields

import de.bitb.spacerace.database.converter.*
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerColor
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class FieldData(
        @Id
        var uuid: Long = 0,
//        @Convert(converter = FieldTypeConverter::class, dbType = String::class)
//        var fieldType: FieldType = FieldType.UNKNOWN,
        @Convert(converter = PlayerColorConverter::class, dbType = String::class)
        var owner: PlayerColor = PlayerColor.NONE) {

//    lateinit var position: ToOne<PositionData>
//    lateinit var connections: ToMany<FieldData>
}
