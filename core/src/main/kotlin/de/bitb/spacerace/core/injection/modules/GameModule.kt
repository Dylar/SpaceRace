package de.bitb.spacerace.core.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.usecase.dispender.AttachItemDispenser
import de.bitb.spacerace.usecase.dispender.PlayerColorDispenser
import de.bitb.spacerace.core.events.commands.CommandDispender
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
