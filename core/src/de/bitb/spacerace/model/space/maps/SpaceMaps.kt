package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.enums.FieldType

enum class SpaceMaps {

    CROSSROAD(),
    TEST_MAP(),
    RANDOM();

    fun createMap(gameController: GameController, spaceMaps: SpaceMaps = this): SpaceMap {
        return when (spaceMaps) {
            SpaceMaps.CROSSROAD -> CrossRoad(gameController)
            SpaceMaps.TEST_MAP -> TestMap(gameController, FieldType.GIFT)
            SpaceMaps.RANDOM -> TestMap(gameController)
        }
    }

}