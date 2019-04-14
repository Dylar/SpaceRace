package de.bitb.spacerace.injection.components

import dagger.Component
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.injection.modules.*
import de.bitb.spacerace.ui.player.PlayerStats
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    UtilsModule::class,
    ControllerModule::class,
    BuilderModule::class]
)
interface AppComponent {
    fun inject(game: MainGame)
    fun inject(inputHandler: InputHandler)

    //Controller
    fun inject(gameController: GameController)

    //UI
    fun inject(gameGuiStage: GameGuiStage)
    fun inject(playerStats: PlayerStats)
}