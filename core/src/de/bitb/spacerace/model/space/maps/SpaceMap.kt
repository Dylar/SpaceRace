package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.model.space.fields.SpaceField
import de.bitb.spacerace.model.space.groups.SpaceGroup

abstract class SpaceMap {
    companion object {
        fun createMap(gameController: GameController, mapType: MapType): SpaceMap {
            return when (mapType) {
                MapType.CROSSROAD -> CrossRoad(gameController)
                MapType.TESTMAP -> TestMap(gameController)
                MapType.RANDOM -> createMap(gameController, MapType.values()[(Math.random() * MapType.values().size).toInt()])
            }
        }
    }

    var startField: SpaceField = SpaceField.NONE
    val groups: MutableList<SpaceGroup> = ArrayList()

}