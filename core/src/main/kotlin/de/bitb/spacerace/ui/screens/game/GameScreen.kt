package de.bitb.spacerace.ui.screens.game

import com.badlogic.gdx.scenes.scene2d.Actor
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.DEBUG_CAMERA_TARGET
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.GameController
import de.bitb.spacerace.core.controller.GraphicController
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.grafik.model.objecthandling.GameImage
import de.bitb.spacerace.ui.game.RoundEndDetails
import de.bitb.spacerace.ui.game.SRRoundEndMenu
import de.bitb.spacerace.ui.player.items.ItemDetailsMenu
import de.bitb.spacerace.ui.player.items.ItemMenu
import de.bitb.spacerace.ui.player.shop.ShopMenu
import de.bitb.spacerace.ui.screens.GuiBackstack
import de.bitb.spacerace.ui.screens.GuiBackstackHandler
import de.bitb.spacerace.ui.screens.GuiNavi
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
        currentZoom--
        zoom()
    }

    fun onZoomMinusClicked() {
        currentZoom++
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
        when (event) {
            is GuiNavi.StorageMenu -> openStorageMenu(event)
            is GuiNavi.ItemDetailMenu -> openItemDetailMenu(event)
            is GuiNavi.EndRoundMenu -> openEndRoundMenu(event)
            is GuiNavi.PlayerEndDetailsMenu -> openPlayerEndDetails(event)
            is GuiNavi.ObtainShopMenu -> openShopMenu(event)
        }.also {
            addToBackstack(event, it, guiStage)
        }
    }

    private fun openPlayerEndDetails(event: GuiNavi.PlayerEndDetailsMenu): Actor =
            RoundEndDetails(guiStage as GameGuiStage, playerDataSource.getDBPlayerByColor(event.player).first())
                    .also { it.isOpen = true }

    private fun openItemDetailMenu(event: GuiNavi.ItemDetailMenu): Actor =
            ItemDetailsMenu(guiStage as GameGuiStage, event.itemType, playerController.currentPlayerData)
                    .also { it.isOpen = true }

    //TODO
    private fun openStorageMenu(event: GuiNavi.StorageMenu): Actor =
            ItemMenu(guiStage as GameGuiStage, playerController.currentPlayerData)
                    .also { it.isOpen = true }

//TODO do it all

    private fun openEndRoundMenu(event: GuiNavi.EndRoundMenu): Actor = SRRoundEndMenu()

    fun openShopMenu(event: GuiNavi.ObtainShopMenu): Actor =
            ShopMenu(guiStage as GameGuiStage)
                    .also { it.isOpen = true }

}
