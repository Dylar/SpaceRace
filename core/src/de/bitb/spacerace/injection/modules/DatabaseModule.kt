package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bornholdtlee.baseproject.injection.ApplicationScope
//import androidx.room.Room
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.DAODispender
import de.bitb.spacerace.database.PlayerDAO

@Module
class DatabaseModule(val daoDispender: DAODispender) {

    @Provides
    @ApplicationScope
    fun playerDAO(): PlayerDAO {
        return daoDispender.getPlayerDAO()
    }

}
