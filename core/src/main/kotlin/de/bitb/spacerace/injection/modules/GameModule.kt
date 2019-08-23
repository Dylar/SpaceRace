package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.events.commands.CommandDispender
import de.bitb.spacerace.core.PlayerColorDispender
import javax.inject.Singleton

@Module
class GameModule {

    @Provides
    @Singleton
    fun providePlayerColorDispender(): PlayerColorDispender {
        return PlayerColorDispender()
    }

    @Provides
    @Singleton
    fun provideCommandDispender(): CommandDispender {
        return CommandDispender()
    }

}
