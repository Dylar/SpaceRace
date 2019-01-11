package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.groups.CircleGroup
import de.bitb.spacerace.model.space.groups.TestGroup

class TestMap(gameController: GameController, fieldType: FieldType = FieldType.RANDOM) : SpaceMap() {

    init {
        val fieldTypes = ArrayList<FieldType>()
        for (fieldType in FieldType.values()) {
            fieldTypes.add(fieldType)
        }

        val crescentGroup = CircleGroup(gameController, offsetX = -SCREEN_HEIGHT.toFloat(), fieldTypes = fieldTypes)
        val centerGroup = TestGroup(gameController, SCREEN_WIDTH.toFloat(), fieldType = fieldType)
        centerGroup.connect(gameController.fieldController, ConnectionPoint.TOP, crescentGroup)

        startField = centerGroup.getField(1)
        groups.add(centerGroup)
        groups.add(crescentGroup)

    }
}