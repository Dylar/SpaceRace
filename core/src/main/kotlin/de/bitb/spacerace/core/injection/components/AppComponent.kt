package de.bitb.spacerace.core.injection.components

import dagger.Component
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.events.commands.phases.NextPhaseCommand
import de.bitb.spacerace.core.events.commands.phases.StartNextRoundCommand
import de.bitb.spacerace.core.events.commands.player.*
import de.bitb.spacerace.core.events.commands.start.LoadGameCommand
import de.bitb.spacerace.core.events.commands.start.SelectMapCommand
import de.bitb.spacerace.core.injection.modules.*
import de.bitb.spacerace.database.player.PlayerRespository
import de.bitb.spacerace.grafik.model.items.ItemGraphic
import de.bitb.spacerace.grafik.model.space.groups.ConnectionList
import de.bitb.spacerace.grafik.model.space.groups.SpaceGroup
import de.bitb.spacerace.ui.base.BaseMenu
import de.bitb.spacerace.ui.player.PlayerStatsGui
import de.bitb.spacerace.ui.player.SRPlayerStatsGui
import de.bitb.spacerace.ui.player.items.ItemDetailsMenu
import de.bitb.spacerace.ui.player.items.ItemMenu
import de.bitb.spacerace.ui.player.shop.ShopDetails
import de.bitb.spacerace.ui.screens.game.GameGuiStage
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.ui.screens.game.GameStage
import de.bitb.spacerace.ui.screens.game.control.GameActionGui
import de.bitb.spacerace.ui.screens.game.control.SRActionGui
import de.bitb.spacerace.ui.screens.game.control.SRViewControlGui
import de.bitb.spacerace.ui.screens.start.control.LoadGameGui
import de.bitb.spacerace.ui.screens.start.control.MapSelectionGui
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

    fun inject(baseMenu: BaseMenu)
    fun inject(baseMenu: ItemMenu)
    fun inject(itemDetailsMenu: ItemDetailsMenu)
    fun inject(playerStatsGui: PlayerStatsGui)
    fun inject(gameActionGui: GameActionGui)
    fun inject(shopDetails: ShopDetails)
    fun inject(mapSelectionGui: MapSelectionGui)

    fun inject(startButtonGui: StartButtonGui)
    fun inject(loadGameGui: LoadGameGui)

    //VisUi
    fun inject(SRActionGui: SRActionGui)
    fun inject(srPlayerStatsGui: SRPlayerStatsGui)
    fun inject(srViewControlGui: SRViewControlGui)

    //GAME
    fun inject(gameScreen: GameScreen)

    fun inject(gameStage: GameStage)

    fun inject(connectionList: ConnectionList)

    //MAP
    fun inject(spaceGroup: SpaceGroup)

    //COMMAND
    fun inject(loadGameCommand: LoadGameCommand)

    fun inject(useItemCommand: UseItemCommand)

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


    fun inject(itemGraphic: ItemGraphic)

}