package de.bitb.spacerace.database.converter

import de.bitb.spacerace.model.player.PlayerColor
import io.objectbox.converter.PropertyConverter

class PlayerColorConverter : PropertyConverter<PlayerColor, Int> {

    override fun convertToDatabaseValue(entityProperty: PlayerColor?): Int? {
        return entityProperty?.color?.toIntBits()
    }

    override fun convertToEntityProperty(databaseValue: Int?): PlayerColor? {
        return databaseValue?.let { PlayerColor.get(it) }
    }

}
