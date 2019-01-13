package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.model.enums.ConnectionPoint
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.space.groups.BoxGroup
import de.bitb.spacerace.model.space.groups.CrossGroup

class CrossRoadMap(gameController: GameController, fieldController: FieldController = gameController.fieldController) : SpaceMap() {

    init {
        val offsetY = Dimensions.SCREEN_HEIGHT * 1.2f
        val offsetX = Dimensions.SCREEN_WIDTH

        val centerGroup = CrossGroup(gameController)
        val upGroup = BoxGroup(gameController, offsetY = offsetY * 1.1f)
        val rightGroup = BoxGroup(gameController, offsetX)
        val downGroup = BoxGroup(gameController, offsetY = -offsetY * 1.1f)
        val leftGroup = BoxGroup(gameController, -offsetX)

        centerGroup.connect(fieldController, ConnectionPoint.TOP, upGroup)
        centerGroup.connect(fieldController, ConnectionPoint.RIGHT, rightGroup)
        centerGroup.connect(fieldController, ConnectionPoint.BOTTOM, downGroup)
        centerGroup.connect(fieldController, ConnectionPoint.LEFT, leftGroup)

        startField = centerGroup.getField(0)
        groups.add(centerGroup)
        groups.add(upGroup)
        groups.add(rightGroup)
        groups.add(downGroup)
        groups.add(leftGroup)

    }
}