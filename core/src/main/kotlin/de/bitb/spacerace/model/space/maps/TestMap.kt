package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_HEIGHT
import de.bitb.spacerace.config.dimensions.Dimensions.SCREEN_WIDTH
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.space.groups.CircleGroup
import de.bitb.spacerace.model.space.groups.TestGroup

class TestMap(vararg fieldType: FieldType) : SpaceMap() {

    init {
        val fieldTypes = ArrayList<FieldType>()
        FieldType.values().forEach { fieldTypes.add(it) }

        val crescentGroup = CircleGroup(offsetX = -SCREEN_HEIGHT, fieldTypes = fieldTypes)
        val centerGroup = TestGroup(fieldType = *fieldType)
        val rightGroup = TestGroup(offsetX = SCREEN_WIDTH, fieldType = *arrayOf(FieldType.RANDOM, FieldType.GOAL))
        val topGroup = TestGroup(offsetY = SCREEN_HEIGHT, fieldType = *arrayOf(FieldType.RANDOM, FieldType.GOAL))
        centerGroup.connect(ConnectionPoint.LEFT, crescentGroup)
        centerGroup.connect(ConnectionPoint.RIGHT, rightGroup)
        centerGroup.connect(ConnectionPoint.TOP, topGroup)

        startField = centerGroup.getField(1)
        groups.add(centerGroup)
        groups.add(crescentGroup)
        groups.add(rightGroup)
        groups.add(topGroup)

    }
}