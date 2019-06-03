package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.model.space.fields.FieldData
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class MapData(
        @Id
        var uuid: Long = 0) {

    lateinit var fields: ToMany<FieldData>
}