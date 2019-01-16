package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.groups.SpinningGroup

class SolarsystemMap(gameController: GameController, vararg fieldType: FieldType) : SpaceMap() {

    init {
        val fieldTypes = ArrayList<FieldType>()
        for (type in FieldType.values()) {
            fieldTypes.add(type)
        }

        val centerGroup1 = SpinningGroup(gameController, fieldType = *fieldType)

        startField = centerGroup1.getField(1)
        groups.add(centerGroup1)

    }
}