package de.bitb.spacerace.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.bitb.spacerace.database.DAODispender
import de.bitb.spacerace.database.PlayerDAO
import de.bitb.spacerace.database.converter.PlayerColorConverter
import de.bitb.spacerace.model.player.PlayerData

@Database(entities = arrayOf(PlayerData::class), version = 1, exportSchema = false)
@TypeConverters(PlayerColorConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun playerDAO(): PlayerDAO
}