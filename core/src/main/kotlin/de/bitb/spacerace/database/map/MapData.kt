package de.bitb.spacerace.database.map

import io.objectbox.BoxStore
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class MapData(
        val name: String = "",
        @Id
        var uuid: Long = 0
) {
    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

    @JvmField
    var fields: ToMany<FieldData> = ToMany(this, MapData_.fields)

    @JvmField
    var startField: ToOne<FieldData> = ToOne(this, MapData_.startField)

}