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

        val centerGroup = TestGroup(fieldType = *fieldType)
        val crescentGroup = CircleGroup(offsetX = -SCREEN_HEIGHT, fieldTypes = fieldTypes)
        val rightGroup = TestGroup(offsetX = SCREEN_WIDTH, fieldType = *arrayOf(FieldType.RANDOM, FieldType.GOAL))
        val topGroup = TestGroup(offsetY = SCREEN_HEIGHT, fieldType = *arrayOf(FieldType.RANDOM, FieldType.GOAL))

        centerGroup.connectGroups(ConnectionPoint.LEFT, crescentGroup)
        centerGroup.connectGroups(ConnectionPoint.RIGHT, rightGroup)
        centerGroup.connectGroups(ConnectionPoint.TOP, topGroup)

        startField = centerGroup.getField(1)
        connections.apply {
            addAll(centerGroup.connections)
            addAll(crescentGroup.connections)
            addAll(rightGroup.connections)
            addAll(topGroup.connections)
        }
        groups.add(centerGroup)
        groups.add(crescentGroup)
        groups.add(rightGroup)
        groups.add(topGroup)

    }
}