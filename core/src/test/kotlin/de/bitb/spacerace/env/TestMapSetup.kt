package de.bitb.spacerace.env

import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.maps.connectTo
import de.bitb.spacerace.model.space.maps.newField
import de.bitb.spacerace.model.space.maps.newMap

fun TestEnvironment.getTestMap() =
        newMap("TEST MAP NAME")
                .apply {
                    startPosition = PositionData(posX = 1f, posY = 1f)
                    val field1 = newField(FieldType.RANDOM, startPosition)
                    val field2 = newField(FieldType.GIFT, startPosition.copy(posX = 2f))
                    val field3 = newField(FieldType.GOAL, startPosition.copy(posX = 2f, posY = 2f))
                    val field4 = newField(FieldType.GOAL, startPosition.copy(posY = 2f))

                    field1 connectTo field2
                    field2 connectTo field3
                    field1 connectTo field4
TODO mach was draus
                    fields.addAll(listOf(field1, field2, field3, field4))
                }