package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.SpaceGroup

abstract class SpaceMap {

    var startField: SpaceField = SpaceField.NONE
    val groups: MutableList<SpaceGroup> = ArrayList()
    val allGoals: MutableList<SpaceField> = ArrayList()

    fun getRandomGoal(): SpaceField {
        return allGoals[(Math.random() * allGoals.size).toInt()]
    }

    fun addAllGoals() {
        groups.forEach { group -> run { group.fields.values.forEach { field -> if (field.fieldType == FieldType.GOAL) allGoals.add(field) } } }
    }
}