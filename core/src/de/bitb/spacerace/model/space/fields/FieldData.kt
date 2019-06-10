package de.bitb.spacerace.model.space.fields

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

//@Entity
//data class FieldData(
//        @Id
//        var uuid: Long = 0,
//        var name:String = ""
////        @Convert(converter = FieldTypeConverter::class, dbType = String::class)
////        var fieldType: FieldType = FieldType.UNKNOWN,
////        @Convert(converter = PlayerColorConverter::class, dbType = String::class)
////        var owner: PlayerColor = PlayerColor.NONE
//) {
//
////    lateinit var position: ToOne<PositionData>
////    lateinit var connections: ToMany<FieldData>
//
//}