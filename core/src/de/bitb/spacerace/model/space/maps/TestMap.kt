package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.groups.CircleGroup
import de.bitb.spacerace.model.space.groups.TestGroup

class TestMap(gameController: GameController, vararg fieldType: FieldType) : SpaceMap() {

    init {
        val fieldTypes = ArrayList<FieldType>()
        for (type in FieldType.values()) {
            fieldTypes.add(type)
        }

        val crescentGroup = CircleGroup(gameController, offsetX = -SCREEN_HEIGHT, fieldTypes = fieldTypes)
        val centerGroup = TestGroup(gameController, fieldType = *fieldType)
        val rightGroup = TestGroup(gameController, offsetX = SCREEN_WIDTH, fieldType = *arrayOf(FieldType.RANDOM, FieldType.GOAL))
        val topGroup = TestGroup(gameController, offsetY = SCREEN_HEIGHT, fieldType = *arrayOf(FieldType.RANDOM, FieldType.GOAL))
        centerGroup.connect(gameController.fieldController, ConnectionPoint.LEFT, crescentGroup)
        centerGroup.connect(gameController.fieldController, ConnectionPoint.RIGHT, rightGroup)
        centerGroup.connect(gameController.fieldController, ConnectionPoint.TOP, topGroup)

        startField = centerGroup.getField(1)
        groups.add(centerGroup)
        groups.add(crescentGroup)
        groups.add(rightGroup)
        groups.add(topGroup)

    }
}