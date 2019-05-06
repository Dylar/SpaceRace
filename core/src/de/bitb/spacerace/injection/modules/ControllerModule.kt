package de.bitb.spacerace.injection.modules

import dagger.Module
import dagger.Provides
import de.bitb.spacerace.controller.FieldController
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import javax.inject.Singleton

@Module
class ControllerModule {

    @Provides
    @Singleton
    fun providePlayerController(): PlayerController {
        return PlayerController()
    }

    @Provides
    @Singleton
    fun provideFieldController(playerController: PlayerController): FieldController {
        return FieldController(playerController)
    }

    @Provides
    @Singleton
    fun provideInputHandler(game: MainGame): InputHandler {
        return InputHandler(game)
    }

}
