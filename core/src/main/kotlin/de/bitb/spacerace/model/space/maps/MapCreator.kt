package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.config.DEBUG_TEST_FIELD
import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.MapData

enum class MapCreator {

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

fun String.createMap() =
        (MapCreator.values()
                .find { it.name == this }
                ?: MapCreator.RANDOM).createMap()

fun SpaceMap.initDefaultMap(): MapData = MapData().apply {
    //create fields
    groups.forEach { spaceGroup ->
        spaceGroup.fields.entries.forEach { field ->
            val spaceField = field.value
            val fieldData = FieldConfigData(
                    fieldType = spaceField.fieldType,
                    gamePosition = spaceField.gamePosition)
            fields.add(fieldData)
            if (spaceField.gamePosition.isPosition(startField.gamePosition))
                startPosition.setPosition(fieldData.gamePosition)
        }
    }

    fields.forEach { fieldData ->
        connections
                .filter { it.isConnected(fieldData.gamePosition) }
                .forEach { connection ->
                    val opposite = connection.getOpposite(fieldData.gamePosition)
                    val oppositeData = fields.find { field -> field.gamePosition.isPosition(opposite.gamePosition) }
                    oppositeData?.also {
                        fieldData.connections.add(it.gamePosition)
                    }
                }
    }

}
