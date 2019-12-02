package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.DEBUG_CAMERA_TARGET
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GameController
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.core.utils.Logger
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.ui.screens.GuiBackstack
import de.bitb.spacerace.ui.screens.GuiBackstackHandler
import de.bitb.spacerace.ui.screens.GuiNavi
import de.bitb.spacerace.ui.screens.game.player.items.SRStorageItemMenu
import de.bitb.spacerace.ui.screens.game.player.items.SRStorageMenu
import de.bitb.spacerace.ui.screens.game.player.shop.SRShopItemMenu
import de.bitb.spacerace.ui.screens.game.player.shop.SRShopMenu
import de.bitb.spacerace.ui.screens.game.round.SRRoundEndMenu
import de.bitb.spacerace.ui.screens.game.round.SRRoundEndPlayerMenu
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class GameScreen(
        game: MainGame,
        previousScreen: BaseScreen
) : BaseScreen(game, previousScreen),
        GuiBackstack by GuiBackstackHandler {

    init {
        MainGame.appComponent.inject(this)
    }

    @Inject //TODO delete me
    protected lateinit var playerDataSource: PlayerDataSource

    @Inject
    protected lateinit var graphicController: GraphicController

    @Inject
    protected lateinit var gameController: GameController

    @Inject
    protected lateinit var playerController: PlayerController

    override fun createGuiStage(): BaseStage {
        return GameGuiStage(this)
    }

    override fun createGameStage(): BaseStage {
        return GameStage(this)
    }

    override fun createBackgroundStage(): BaseStage {
        return BackgroundStage(this)
    }

    override fun getCameraTarget(): GameImage? {
        return DEBUG_CAMERA_TARGET ?: graphicController.currentPlayerGraphic.getGameImage()
    }

    fun onZoomPlusClicked() {
        currentZoom -= .3f
        zoom()
    }

    fun onZoomMinusClicked() {
        currentZoom += .3f
        zoom()
    }

    override fun show() {
        super.show()
        addEntities()
        EventBus.getDefault().register(this)
    }

    private fun addEntities() {
        val gameStage = gameStage as GameStage
        gameStage.clear()
        gameStage.addEntitiesToMap()
    }

    override fun hide() {
        super.hide()
        gameController.clear()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun navigateEvent(event: GuiNavi) {
        Logger.justPrint("Open Gui: ${event::class.simpleName}, ${event.player}")
        when (event) {
            is GuiNavi.StorageMenu -> SRStorageMenu(event.player)
            is GuiNavi.StorageDetailMenu -> SRStorageItemMenu(event.player, event.itemType)
            is GuiNavi.EndRoundMenu -> SRRoundEndMenu()
            is GuiNavi.PlayerEndDetailsMenu -> SRRoundEndPlayerMenu(event.player)
            is GuiNavi.ShopMenu -> SRShopMenu(event.player)
            is GuiNavi.ShopDetailMenu -> SRShopItemMenu(event.player, event.itemType)
        }.also {
            addToBackstack(event, it, guiStage)
        }
    }

}
