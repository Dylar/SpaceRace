package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.model.objecthandling.NONE_POSITION
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.NONE_FIELD
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.SpaceGroup

abstract class SpaceMap {

    //TODO save maps in db

    var startField: SpaceField = NONE_FIELD
    val groups: MutableList<SpaceGroup> = ArrayList()
    var firstGoal: PositionData = NONE_POSITION

//    fun addAllGoals() {
//        allGoals.apply {
//            addAll(groups
//                    .map { group -> group.fields.values }
//                    .flatten()
//                    .filter { it.fieldType == FieldType.GOAL })
//        }

//    }
}