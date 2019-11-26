package de.bitb.spacerace.grafik.model.space.maps

import de.bitb.spacerace.config.DEBUG_TEST_FIELD
import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.grafik.model.enums.FieldType
import kotlin.random.Random

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
        RANDOM -> {
            val fieldTypes = FieldType.values()
            val selectedTypes = mutableListOf<FieldType>()
            repeat(10){
                val index = Random.nextInt(fieldTypes.size - 1)
                selectedTypes.add(FieldType.values().first { it.ordinal == index })
            }
            TestMap(*selectedTypes.toTypedArray())
        }
    }

}

fun String.createMap() =
        (MapCreator.values()
                .find { it.name == this }
                ?: MapCreator.TEST_MAP).createMap()

fun SpaceMap.initDefaultMap(name: String): MapData = MapData(name).apply {
    //create fields
    groups.forEach { spaceGroup ->
        spaceGroup.fields.entries.forEach { field ->
            val spaceField = field.value

            val rotateAround = null //spaceField.getGameImage().let { if (it.followImage != GameImage.NONE_GAMEIMAGE) it.p } TODO spin me around
            val fieldData = FieldConfigData(
                    fieldType = spaceField.fieldType,
                    gamePosition = spaceField.gamePosition,
                    rotateAround = rotateAround)
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
