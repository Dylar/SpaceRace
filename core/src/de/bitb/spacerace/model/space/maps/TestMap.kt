package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.space.groups.RandomGroup

class TestMap(gameController: GameController) : SpaceMap() {

    init {
        val centerGroup = RandomGroup(gameController)

        startField = centerGroup.getField(0)
        groups.add(centerGroup)

    }
}