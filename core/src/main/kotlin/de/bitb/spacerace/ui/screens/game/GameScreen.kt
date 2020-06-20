package de.bitb.spacerace.ui.screens.game

import com.badlogic.gdx.scenes.scene2d.Stage
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.CameraRenderer
import de.bitb.spacerace.base.CameraStateRenderer
import de.bitb.spacerace.config.DEBUG_CAMERA_TARGET
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GameController
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.core.utils.Logger
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.ui.screens.BackgroundStage
import de.bitb.spacerace.ui.screens.GuiBackstack
import de.bitb.spacerace.ui.screens.GuiBackstackHandler
import de.bitb.spacerace.ui.screens.NaviRoute
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
        previousScreen: BaseScreen
) : BaseScreen(previousScreen),
        GuiBackstack by GuiBackstackHandler,
        CameraRenderer by CameraStateRenderer() {

    init {
        MainGame.appComponent.inject(this)
    }

    @Inject
    protected lateinit var graphicController: GraphicController

    @Inject
    protected lateinit var gameController: GameController

    @Inject
    protected lateinit var playerController: PlayerController

    private lateinit var guiStage: GameGuiStage

    override lateinit var allStages: List<Stage>
    override lateinit var inputStages: List<Stage>

    val cameraTarget: GameImage?
        get() = DEBUG_CAMERA_TARGET ?: graphicController.currentPlayerGraphic.getGameImage()

    override fun show() {
        initStages()
        super.show()
        EventBus.getDefault().register(this)
    }

    private fun initStages() {
        guiStage = GameGuiStage(this)
        val gameStage = GameStage(this)
        val backgroundStage = BackgroundStage(this)
        allStages = mutableListOf(backgroundStage, gameStage, guiStage)
        inputStages = mutableListOf(guiStage, gameStage)

        gameStage.clear()
        gameStage.addEntitiesToMap()
        initCamera(
                baseScreen = this,
                entityStage = gameStage,
                backgroundStage = backgroundStage,
                centerOnEntity = cameraTarget
        )
    }

    fun onZoomPlusClicked() = addZoom(-.3f)
    fun onZoomMinusClicked() = addZoom(.3f)

    override fun hide() {
        super.hide()
        gameController.clear()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun navigateEvent(event: NaviRoute) {
        Logger.justPrint("Open Gui: ${event::class.simpleName}, ${event.player}")
        when (event) {
            is NaviRoute.StorageMenu -> SRStorageMenu(event.player)
            is NaviRoute.StorageDetailMenu -> SRStorageItemMenu(event.player, event.itemType)
            is NaviRoute.EndRoundMenu -> SRRoundEndMenu()
            is NaviRoute.PlayerEndDetailsMenu -> SRRoundEndPlayerMenu(event.player)
            is NaviRoute.ShopMenu -> SRShopMenu(event.player)
            is NaviRoute.ShopDetailMenu -> SRShopItemMenu(event.player, event.itemType)
        }.also {
            addToBackstack(event, it, guiStage)
        }
    }

}
