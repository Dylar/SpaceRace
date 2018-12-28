package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.config.DEBUG_TEST_FIELD
import de.bitb.spacerace.controller.GameController

enum class SpaceMaps {

    CROSSROAD(),
    TEST_MAP(),
    RANDOM();

    fun createMap(gameController: GameController, spaceMaps: SpaceMaps = this): SpaceMap {
        return when (spaceMaps) {
            SpaceMaps.CROSSROAD -> CrossRoad(gameController)
            SpaceMaps.TEST_MAP -> TestMap(gameController, DEBUG_TEST_FIELD)
            SpaceMaps.RANDOM -> TestMap(gameController)
        }
    }

}