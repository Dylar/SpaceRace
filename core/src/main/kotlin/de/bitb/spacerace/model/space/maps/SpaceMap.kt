package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.config.DEBUG_WIN_FIELD
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.fields.NONE_FIELD
import de.bitb.spacerace.model.space.groups.SpaceGroup

abstract class SpaceMap {

    //TODO save maps in db

    var startField: SpaceField = NONE_FIELD
    val groups: MutableList<SpaceGroup> = ArrayList()
    val allGoals: MutableList<SpaceField> = ArrayList()

    fun addAllGoals() {
        allGoals.apply {
            addAll(groups
                    .map { group -> group.fields.values }
                    .flatten()
                    .filter { it.fieldType == FieldType.GOAL })
        }

    }
}