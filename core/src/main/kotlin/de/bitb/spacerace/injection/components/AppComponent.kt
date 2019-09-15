package de.bitb.spacerace.injection.components

import dagger.Component
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.database.player.PlayerRespository
import de.bitb.spacerace.events.commands.phases.NextPhaseCommand
import de.bitb.spacerace.events.commands.phases.StartNextRoundCommand
import de.bitb.spacerace.events.commands.player.BuyItemCommand
import de.bitb.spacerace.events.commands.player.DiceCommand
import de.bitb.spacerace.events.commands.player.MoveCommand
import de.bitb.spacerace.events.commands.player.SellItemCommand
import de.bitb.spacerace.events.commands.start.LoadGameCommand
import de.bitb.spacerace.events.commands.start.SelectMapCommand
import de.bitb.spacerace.injection.modules.*
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.space.groups.ConnectionList
import de.bitb.spacerace.model.space.groups.SpaceGroup
import de.bitb.spacerace.ui.base.BaseMenu
import de.bitb.spacerace.ui.game.RoundEndDetails
import de.bitb.spacerace.ui.player.PlayerStatsGui
import de.bitb.spacerace.ui.player.items.ItemDetails
import de.bitb.spacerace.ui.player.shop.ShopDetails
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.ui.screens.game.GameStage
import de.bitb.spacerace.ui.screens.game.control.GameActionGui
import de.bitb.spacerace.ui.screens.start.control.LoadGameGui
import de.bitb.spacerace.ui.screens.start.control.StartButtonGui
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ApplicationModule::class,
//    NetworkModule::class,
            DatabaseModule::class,
            GameModule::class,
            UseCaseModule::class,
//    UtilsModule::class,
//    BuilderModule::class,
            ControllerModule::class]
)
interface AppComponent {
    fun inject(game: MainGame)

    //CONTROLLER
//    fun inject(playerController: PlayerController)

    //UI
    fun inject(baseGuiStage: BaseGuiStage)
    fun inject(gameGuiStage: GameGuiStage)

    fun inject(baseMenu: BaseMenu)
    fun inject(itemDetails: ItemDetails)
    fun inject(baseMenu: RoundEndDetails)
    fun inject(playerStatsGui: PlayerStatsGui)
    fun inject(gameActionGui: GameActionGui)
    fun inject(shopDetails: ShopDetails)

    fun inject(startButtonGui: StartButtonGui)
    fun inject(loadGameGui: LoadGameGui)

    //GAME
    fun inject(gameScreen: GameScreen)

    fun inject(gameStage: GameStage)

    fun inject(connectionList: ConnectionList)

    //MAP
    fun inject(spaceGroup: SpaceGroup)

    //COMMAND
    fun inject(loadGameCommand: LoadGameCommand)

    fun inject(nextPhaseCommand: NextPhaseCommand)
    fun inject(startNextRoundCommand: StartNextRoundCommand)
    fun inject(moveCommand: MoveCommand)
    fun inject(diceCommand: DiceCommand)
    fun inject(selectMapCommand: SelectMapCommand)
    fun inject(sellItemCommand: SellItemCommand)
    fun inject(buyItemCommand: BuyItemCommand)

    //USE CASE

    //DATABASE
    fun inject(playerRespository: PlayerRespository)


    fun inject(item: Item)
}