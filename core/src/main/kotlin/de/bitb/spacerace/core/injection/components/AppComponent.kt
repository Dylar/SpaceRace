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
import de.bitb.spacerace.ui.base.SRWindowGui
import de.bitb.spacerace.ui.screens.game.round.SRRoundEndMenu
import de.bitb.spacerace.ui.screens.game.round.SRRoundEndPlayerMenu
import de.bitb.spacerace.ui.screens.game.player.SRPlayerStatsGui
import de.bitb.spacerace.ui.screens.game.player.items.SRStorageItemMenu
import de.bitb.spacerace.ui.screens.game.player.items.SRStorageMenu
import de.bitb.spacerace.ui.screens.game.player.shop.SRShopItemMenu
import de.bitb.spacerace.ui.screens.game.GameScreen
import de.bitb.spacerace.ui.screens.game.GameStage
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

    fun inject(mapSelectionGui: MapSelectionGui)

    fun inject(startButtonGui: StartButtonGui)
    fun inject(loadGameGui: LoadGameGui)

    //VisUi
    fun inject(srActionGui: SRActionGui)
    fun inject(srPlayerStatsGui: SRPlayerStatsGui)
    fun inject(srViewControlGui: SRViewControlGui)
    fun inject(srRoundEndMenu: SRRoundEndMenu)
    fun inject(srStorageMenu: SRStorageMenu)
    fun inject(srStorageItemMenu: SRStorageItemMenu)
    fun inject(srWindowGui: SRWindowGui)
    fun inject(srRoundEndPlayerMenu: SRRoundEndPlayerMenu)
    fun inject(srShopItemMenu: SRShopItemMenu)

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