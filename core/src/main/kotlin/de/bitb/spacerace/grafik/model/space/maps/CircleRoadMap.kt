package de.bitb.spacerace.grafik.model.space.maps

import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.grafik.model.enums.ConnectionPoint
import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.space.groups.BoxGroup
import de.bitb.spacerace.grafik.model.space.groups.CircleGroup

class CircleRoadMap()
    : SpaceMap() {

    init {
        val offsetY = Dimensions.SCREEN_HEIGHT * 1.2f
        val offsetX = Dimensions.SCREEN_WIDTH.toFloat()

        val fieldTypes = ArrayList<FieldType>()
        fieldTypes.add(FieldType.TUNNEL)
        fieldTypes.add(FieldType.LOSE)
        fieldTypes.add(FieldType.SHOP)
        fieldTypes.add(FieldType.GIFT)
        fieldTypes.add(FieldType.WIN)
        fieldTypes.add(FieldType.TUNNEL)
        fieldTypes.add(FieldType.LOSE)
        fieldTypes.add(FieldType.GOAL)

        val centerGroup = CircleGroup(fieldTypes = fieldTypes)
        val upGroup = BoxGroup(offsetY = offsetY * 1.2f)
        val rightGroup = BoxGroup(offsetX)
        val downGroup = BoxGroup(offsetY = -offsetY / 1.2f)
        val leftGroup = BoxGroup(-offsetX)

        centerGroup.connectGroups(ConnectionPoint.TOP, upGroup)
        centerGroup.connectGroups(ConnectionPoint.RIGHT, rightGroup)
        centerGroup.connectGroups(ConnectionPoint.BOTTOM, downGroup)
        centerGroup.connectGroups(ConnectionPoint.LEFT, leftGroup)

        startField = centerGroup.getField(0)
        connections.apply {
            addAll(centerGroup.connections)
            addAll(upGroup.connections)
            addAll(rightGroup.connections)
            addAll(downGroup.connections)
            addAll(leftGroup.connections)
        }
        groups.add(centerGroup)
        groups.add(upGroup)
        groups.add(rightGroup)
        groups.add(downGroup)
        groups.add(leftGroup)

    }
}