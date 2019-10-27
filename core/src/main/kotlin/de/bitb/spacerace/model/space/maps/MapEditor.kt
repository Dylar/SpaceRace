package de.bitb.spacerace.model.space.maps

import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.objecthandling.PositionData

fun newMap(
        name: String
) = MapData(name).apply {

}

fun newField(
        fieldType: FieldType,
        position: PositionData
) = FieldConfigData(
        gamePosition = position,
        fieldType = fieldType
)

infix fun FieldConfigData.connectTo(field: FieldConfigData) {
    connections.add(field.gamePosition)
    field.connections.add(gamePosition)
}

infix fun FieldConfigData.rotateAround(point: PositionData?) {
    rotateAround = point //TODO do me
}