package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.events.commands.CommandDispender
import de.bitb.spacerace.core.PlayerColorDispenser
import javax.inject.Singleton

@Module
class GameModule {

    @Provides
    @Singleton
    fun providePlayerColorDispender(): PlayerColorDispenser {
        return PlayerColorDispenser()
    }

    @Provides
    @Singleton
    fun provideCommandDispender(): CommandDispender {
        return CommandDispender()
    }

}
