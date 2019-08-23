package de.bitb.spacerace.database.converter

import de.bitb.spacerace.model.player.PlayerColor
import io.objectbox.converter.PropertyConverter

class PlayerColorConverter : PropertyConverter<PlayerColor, String> {

    override fun convertToDatabaseValue(entityProperty: PlayerColor?) =
            entityProperty?.toString()

    override fun convertToEntityProperty(databaseValue: String?) =
            databaseValue?.let { PlayerColor.get(it) }

}
