package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.model.objecthandling.NONE_POSITION
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.NONE_FIELD
import de.bitb.spacerace.model.space.fields.SpaceConnection
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.ConnectionList
import de.bitb.spacerace.model.space.groups.SpaceGroup

abstract class SpaceMap {

    //TODO save maps in db - only graphics make nu meqefnsjkdanbvsbmas

    var startField: SpaceField = NONE_FIELD
    var firstGoal: PositionData = NONE_POSITION

    val groups: MutableList<SpaceGroup> = ArrayList()
    var connections: ConnectionList = ConnectionList()

}