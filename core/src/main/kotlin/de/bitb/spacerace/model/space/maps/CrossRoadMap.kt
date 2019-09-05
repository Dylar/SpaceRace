package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.model.space.groups.BoxGroup
import de.bitb.spacerace.model.space.groups.CrossGroup

class CrossRoadMap()
    : SpaceMap() {

    init {
        val offsetY = Dimensions.SCREEN_HEIGHT * 1.2f
        val offsetX = Dimensions.SCREEN_WIDTH

        val centerGroup = CrossGroup()
        val upGroup = BoxGroup(offsetY = offsetY * 1.1f)
        val rightGroup = BoxGroup(offsetX)
        val downGroup = BoxGroup(offsetY = -offsetY * 1.1f)
        val leftGroup = BoxGroup(-offsetX)

        centerGroup.connect(ConnectionPoint.TOP, upGroup)
        centerGroup.connect(ConnectionPoint.RIGHT, rightGroup)
        centerGroup.connect(ConnectionPoint.BOTTOM, downGroup)
        centerGroup.connect(ConnectionPoint.LEFT, leftGroup)

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