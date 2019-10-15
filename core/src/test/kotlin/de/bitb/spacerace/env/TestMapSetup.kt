package de.bitb.spacerace.env

import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.maps.connectTo
import de.bitb.spacerace.model.space.maps.newField
import de.bitb.spacerace.model.space.maps.newMap

fun TestEnvironment.createTestMap() =
        newMap("TEST MAP NAME")
                .apply {
                    startPosition = PositionData(posX = 1f, posY = 1f)

                    val leftBottomField = newField(FieldType.RANDOM, startPosition)
                    val leftTopField = newField(FieldType.GIFT, startPosition.copy(posX = 2f))
                    val centerBottomField = newField(FieldType.GOAL, startPosition.copy(posY = 2f))
                    val centerTopField = newField(FieldType.GOAL, startPosition.copy(posX = 2f, posY = 2f))

                    leftBottomField connectTo centerBottomField
                    leftBottomField connectTo leftTopField
                    leftTopField connectTo centerTopField

                    fields.addAll(listOf(leftBottomField, leftTopField, centerBottomField, centerTopField))
                }


fun TestEnvironment.getField() {

}