package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.database.PlayerColorDispender
import de.bitb.spacerace.database.PlayerDataSource
import de.bitb.spacerace.database.PlayerRespository
import de.bitb.spacerace.model.player.MyObjectBox
import de.bitb.spacerace.model.player.PlayerData
import io.objectbox.Box
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
class GameModule {

    @Provides
    @Singleton
    fun providePlayerColorDispender(): PlayerColorDispender {
        return PlayerColorDispender()
    }

}
