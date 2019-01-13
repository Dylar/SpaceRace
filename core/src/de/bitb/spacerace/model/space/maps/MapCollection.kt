package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.config.DEBUG_TEST_FIELD
import de.bitb.spacerace.controller.GameController

enum class MapCollection {

    CROSSROAD(),
    CIRCLEROAD(),
    TEST_MAP(),
    RANDOM();

    fun createMap(gameController: GameController, mapCollection: MapCollection = this): SpaceMap {
        val map = when (mapCollection) {
            MapCollection.CIRCLEROAD -> CircleRoadMap(gameController)
            MapCollection.CROSSROAD -> CrossRoadMap(gameController)
            MapCollection.TEST_MAP -> TestMap(gameController, DEBUG_TEST_FIELD[0])
            MapCollection.RANDOM -> TestMap(gameController)
        }
        map.addAllGoals()
        return map
    }

}