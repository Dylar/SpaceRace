package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.database.map.FieldData
import io.objectbox.BoxStore
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

//@Entity
//data class MapData(
//        val name:String,
//        @Id
//        var uuid: Long = 0
//) {
//
//    @Transient
//    @JvmField
//    protected var __boxStore: BoxStore? = null
//
//    lateinit var fields: ToMany<FieldData>
//}