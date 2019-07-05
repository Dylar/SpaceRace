package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.database.player.PlayerColorDispender
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import javax.inject.Singleton

@Module
class UseCaseModule {

//    @Provides
//    fun provideObserveCurrentPlayerUseCase(
//            playerDataSource: PlayerDataSource,
//            playerColorDispender: PlayerColorDispender
//    ) = ObserveCurrentPlayerUseCase(playerDataSource, playerColorDispender)

}
