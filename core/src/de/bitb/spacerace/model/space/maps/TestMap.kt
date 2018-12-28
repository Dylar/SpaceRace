package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.groups.TestGroup

class TestMap(gameController: GameController, fieldType: FieldType = FieldType.RANDOM) : SpaceMap() {

    init {
        val centerGroup = TestGroup(gameController, fieldType = fieldType)

        startField = centerGroup.getField(0)
        groups.add(centerGroup)

    }
}