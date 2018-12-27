package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.space.groups.CircleGroup
import de.bitb.spacerace.model.space.groups.CrossGroup

class CrossRoad(gameController: GameController) : SpaceMap() {

    init {
        val offsetY = Dimensions.SCREEN_HEIGHT * 1.6f
        val offsetX = Dimensions.SCREEN_WIDTH.toFloat()

        val centerGroup = CrossGroup(gameController)
        val upGroup = CircleGroup(gameController, offsetY = offsetY)
        val rightGroup = CircleGroup(gameController, offsetX)
        val downGroup = CircleGroup(gameController, offsetY = -offsetY)
        val leftGroup = CircleGroup(gameController, -offsetX)

        centerGroup.connect(ConnectionPoint.TOP, upGroup)
        centerGroup.connect(ConnectionPoint.RIGHT, rightGroup)
        centerGroup.connect(ConnectionPoint.BOTTOM, downGroup)
        centerGroup.connect(ConnectionPoint.LEFT, leftGroup)

        startField = centerGroup.getField(0)
        groups.add(centerGroup)
        groups.add(upGroup)
        groups.add(rightGroup)
        groups.add(downGroup)
        groups.add(leftGroup)

    }
}