package de.bitb.spacerace.env

import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.maps.connectTo
import de.bitb.spacerace.model.space.maps.newField
import de.bitb.spacerace.model.space.maps.newMap


const val TEST_MAP_NAME = "UNIT TEST"
const val TEST_POSX = 500f
const val TEST_POSY = 500f

fun TestEnvironment.createTestMap(
) = createMap()

fun createMap(name: String = TEST_MAP_NAME,
              startFieldType: FieldType = FieldType.RANDOM,
              leftTopFieldType: FieldType = FieldType.GIFT,
              centerBottomFieldType: FieldType = FieldType.GOAL,
              centerTopFieldType: FieldType = FieldType.GOAL
) = newMap(name).apply {
    startPosition = PositionData(posX = TEST_POSX, posY = TEST_POSY)

    val leftBottomField = newField(startFieldType, startPosition)
    val leftTopField = newField(leftTopFieldType, startPosition.copy(posY = TEST_POSY * 2))
    val centerBottomField = newField(centerBottomFieldType, startPosition.copy(posX = TEST_POSX * 2))
    val centerTopField = newField(centerTopFieldType, startPosition.copy(posX = TEST_POSX * 2f, posY = TEST_POSY * 2f))

    leftBottomField connectTo centerBottomField
    leftBottomField connectTo leftTopField
    leftTopField connectTo centerTopField

    fields.addAll(listOf(leftBottomField, leftTopField, centerBottomField, centerTopField))
}