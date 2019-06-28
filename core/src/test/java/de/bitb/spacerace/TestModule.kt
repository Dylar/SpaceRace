package de.bitb.spacerace


import dagger.Module
import dagger.Provides
import de.bitb.spacerace.core.MainGame
import javax.inject.Singleton

@Module
class TestModule() {

    @Provides
    @Singleton
    fun provideGame(): TestGame {
        return game
    }

}
