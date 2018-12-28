package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.SpaceGroup

abstract class SpaceMap {
    var startField: SpaceField = SpaceField.NONE
    val groups: MutableList<SpaceGroup> = ArrayList()

}