package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.config.DEBUG_TEST_FIELD

enum class MapCollection {

    CROSSROAD(),
    CIRCLEROAD(),
    SOLARROAD(),
    TEST_MAP(),
    RANDOM();

    fun createMap(): SpaceMap = when (this) {
        CIRCLEROAD -> CircleRoadMap()
        CROSSROAD -> CrossRoadMap()
        SOLARROAD -> SolarsystemMap(*DEBUG_TEST_FIELD.toTypedArray())
        TEST_MAP -> TestMap(*DEBUG_TEST_FIELD.toTypedArray())
        RANDOM -> TestMap()
    }

}