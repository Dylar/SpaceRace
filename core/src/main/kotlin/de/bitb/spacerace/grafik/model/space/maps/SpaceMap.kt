package de.bitb.spacerace.grafik.model.space.maps

import de.bitb.spacerace.grafik.model.space.fields.NONE_SPACE_FIELD
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic
import de.bitb.spacerace.grafik.model.space.groups.ConnectionList
import de.bitb.spacerace.grafik.model.space.groups.SpaceGroup

abstract class SpaceMap {

    //TODO delete me after editor done

    var startField: FieldGraphic = NONE_SPACE_FIELD

    val groups: MutableList<SpaceGroup> = ArrayList()
    var connections: ConnectionList = ConnectionList()

}