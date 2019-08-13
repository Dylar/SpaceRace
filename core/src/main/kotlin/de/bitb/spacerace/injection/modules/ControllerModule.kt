package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import javax.inject.Singleton

@Module
class ControllerModule {

//    @Provides
//    @Singleton
//    fun providePlayerController(usecase: ObserveCurrentPlayerUseCase) = PlayerController()

//    @Provides
//    @Singleton
//    fun provideFieldController(playerController: PlayerController): FieldController {
//        return FieldController(playerController)
//    }

//    @Provides
//    @Singleton
//    fun provideInputHandler() = InputHandler()

}
