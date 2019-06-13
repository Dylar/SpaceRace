package de.bitb.spacerace.injection.modules


import dagger.Module
import dagger.Provides
import de.bitb.spacerace.core.MainGame
import de.bornholdtlee.baseproject.injection.ApplicationScope

@Module
class ApplicationModule(private val game: MainGame) {

    @Provides
    @ApplicationScope
    fun provideGame(): MainGame {
        return game
    }

}
