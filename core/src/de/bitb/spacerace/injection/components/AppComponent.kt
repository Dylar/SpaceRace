package de.bitb.spacerace.injection.components

import dagger.Component
import de.bitb.spacerace.controller.GameController
import de.bitb.spacerace.controller.InputHandler
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.events.commands.phases.EndRoundCommand
import de.bitb.spacerace.injection.modules.*
import de.bitb.spacerace.ui.player.PlayerStats
import de.bitb.spacerace.ui.player.shop.ShopDetails
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.ui.screens.game.GameStage
import de.bitb.spacerace.ui.screens.game.control.GameControl
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    GameModule::class,
    UtilsModule::class,
    ControllerModule::class,
    BuilderModule::class]
)
interface AppComponent {
    fun inject(game: MainGame)
    fun inject(inputHandler: InputHandler)

    //CONTROLLER
    fun inject(gameController: GameController)

    //UI
    fun inject(gameGuiStage: GameGuiStage)

    fun inject(playerStats: PlayerStats)
    fun inject(gameControl: GameControl)
    fun inject(shopDetails: ShopDetails)

    //GAME
    fun inject(gameScreen: GameScreen)

    fun inject(gameStage: GameStage)

    //COMMAND
    fun inject(endRoundCommand: EndRoundCommand)

}