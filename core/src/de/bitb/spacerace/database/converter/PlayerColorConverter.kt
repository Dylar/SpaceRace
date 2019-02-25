package de.bitb.spacerace.database.converter

import androidx.room.TypeConverter
import de.bitb.spacerace.model.player.PlayerColor

class PlayerColorConverter {

    @TypeConverter
    fun toPlayerColor(value: Int?): PlayerColor? {
        return value?.let { PlayerColor.get(it) }
    }

    @TypeConverter
    fun toInt(value: PlayerColor?): Int? {
        return value?.color?.toIntBits()
    }
}
