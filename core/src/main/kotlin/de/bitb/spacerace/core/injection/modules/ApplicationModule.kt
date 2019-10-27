package de.bitb.spacerace.core.injection.modules


import dagger.Module
import dagger.Provides
import de.bitb.spacerace.core.MainGame
import javax.inject.Singleton

@Module
class ApplicationModule(private val game: MainGame) {

    @Provides
    @Singleton
    fun provideGame(): MainGame {
        return game
    }

}
