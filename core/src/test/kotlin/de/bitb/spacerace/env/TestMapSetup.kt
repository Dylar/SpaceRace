package de.bitb.spacerace.env

import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.maps.connectTo
import de.bitb.spacerace.model.space.maps.newField
import de.bitb.spacerace.model.space.maps.newMap


const val TEST_MAP_NAME = "UNIT TEST"
const val TEST_POSX = 500f
const val TEST_POSY = 500f

fun createTestMap(
        firstStep: FieldType = FieldType.GIFT,
        bottomGoal: FieldType = FieldType.GOAL,
        topGoal: FieldType = FieldType.GOAL,
        startField: FieldType = FieldType.GIFT
) =
        newMap(TEST_MAP_NAME).apply {
            startPosition = PositionData(posX = TEST_POSX, posY = TEST_POSY)

            val leftBottomField = newField(startField, startPosition)
            val leftTopField = newField(firstStep, startPosition.copy(posY = TEST_POSY * 2))
            val centerBottomField = newField(bottomGoal, startPosition.copy(posX = TEST_POSX * 2))
            val centerTopField = newField(topGoal, startPosition.copy(posX = TEST_POSX * 2f, posY = TEST_POSY * 2f))

            leftBottomField connectTo centerBottomField
            leftBottomField connectTo leftTopField
            leftTopField connectTo centerTopField

            fields.addAll(listOf(leftBottomField, leftTopField, centerBottomField, centerTopField))
        }