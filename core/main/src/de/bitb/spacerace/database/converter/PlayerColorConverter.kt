package de.bitb.spacerace.database.converter

import de.bitb.spacerace.model.player.PlayerColor
import io.objectbox.converter.PropertyConverter

class PlayerColorConverter : PropertyConverter<PlayerColor, String> {

    override fun convertToDatabaseValue(entityProperty: PlayerColor?): String? {
        return entityProperty?.toString()
    }

    override fun convertToEntityProperty(databaseValue: String?): PlayerColor? {
        return databaseValue?.let { PlayerColor.get(it) }
    }

}
