package de.bitb.spacerace.injection


import dagger.Module
import dagger.Provides
import de.bitb.spacerace.TestGame
import de.bitb.spacerace.game
import javax.inject.Singleton

@Module
class TestModule() {

    @Provides
    @Singleton
    fun provideGame(): TestGame {
        return game
    }

}
