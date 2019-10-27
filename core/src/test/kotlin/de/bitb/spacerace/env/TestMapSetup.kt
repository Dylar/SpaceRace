package de.bitb.spacerace.env

import de.bitb.spacerace.grafik.model.enums.FieldType
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.space.maps.connectTo
import de.bitb.spacerace.grafik.model.space.maps.newField
import de.bitb.spacerace.grafik.model.space.maps.newMap

const val TEST_MAP_NAME = "UNIT TEST MAP"
const val TEST_POSX = 500f
const val TEST_POSY = 500f
//const val TEST_POSX = 350f
//const val TEST_POSY = 350f

fun createTestMap(
        name: String = TEST_MAP_NAME,
        firstStep: FieldType = FieldType.GIFT,
        bottomGoal: FieldType = FieldType.GOAL,
        topGoal: FieldType = FieldType.GOAL,
        startField: FieldType = FieldType.GIFT,
        sideStep: FieldType = FieldType.UNKNOWN,
        circleStep1: FieldType = FieldType.UNKNOWN,
        circleStep2: FieldType = FieldType.UNKNOWN,
        bridge: FieldType = FieldType.UNKNOWN
) =
        newMap(name).apply {
            startPosition = PositionData(posX = TEST_POSX, posY = TEST_POSY)

            val leftBottomField = newField(startField, startPosition)
            val leftTopField = newField(firstStep, startPosition.copy(posY = TEST_POSY * 2))
            val centerBottomField = newField(bottomGoal, startPosition.copy(posX = TEST_POSX * 2))
            val centerTopField = newField(topGoal, startPosition.copy(posX = TEST_POSX * 2f, posY = TEST_POSY * 2f))

            val sideStepField = newField(sideStep, startPosition.copy(posX = TEST_POSX * .3f, posY = TEST_POSY * .7f))
            val circleStep1Field = newField(circleStep1, startPosition.copy(posY = TEST_POSY * 3))
            val circleStep2Field = newField(circleStep2, startPosition.copy(posX = TEST_POSX * 0, posY = TEST_POSY * 2))

            val bridgeField = newField(bridge, startPosition.copy(posX = TEST_POSX * 3, posY = TEST_POSY * 1.5f))

            leftBottomField connectTo sideStepField
            leftBottomField connectTo centerBottomField
            leftBottomField connectTo leftTopField
            leftTopField connectTo centerTopField

            centerTopField connectTo bridgeField
            centerBottomField connectTo bridgeField

            leftTopField connectTo circleStep1Field
            circleStep1Field connectTo circleStep2Field
            circleStep2Field connectTo sideStepField

            fields.addAll(listOf(leftBottomField, leftTopField, centerBottomField, centerTopField, sideStepField, circleStep1Field, circleStep2Field, bridgeField))
        }