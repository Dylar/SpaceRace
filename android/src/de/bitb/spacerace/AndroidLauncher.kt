package de.bitb.spacerace

import android.content.Context
import android.os.Bundle
import androidx.room.Room
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.Database

class AndroidLauncher : AndroidApplication() {

//        @Provides
//    @ApplicationScope
    fun roomDatabase(application: Context): Database {
        return Room.databaseBuilder(
                application,
                Database::class.java, "Database"
        ).build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        initialize(MainGame(roomDatabase(this)), config)
    }
}
