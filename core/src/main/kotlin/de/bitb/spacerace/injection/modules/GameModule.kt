package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.core.AttachItemDispenser
import de.bitb.spacerace.core.PlayerColorDispenser
import de.bitb.spacerace.events.commands.CommandDispender
import javax.inject.Singleton

@Module
class GameModule {

    @Provides
    @Singleton
    fun provideCommandDispender(): CommandDispender = CommandDispender()

    @Provides
    @Singleton
    fun providePlayerColorDispender(): PlayerColorDispenser = PlayerColorDispenser()

    @Provides
    @Singleton
    fun provideAttachItemDispender(): AttachItemDispenser = AttachItemDispenser()


}
