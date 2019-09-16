package de.bitb.spacerace.ui.screens.game

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.CAMERA_TARGET
import de.bitb.spacerace.controller.GraphicController
import de.bitb.spacerace.controller.PlayerController
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.model.objecthandling.GameImage
import io.objectbox.BoxStore
import javax.inject.Inject

class GameScreen(
        game: MainGame,
        previousScreen: BaseScreen
) : BaseScreen(game, previousScreen) {

    init {
        MainGame.appComponent.inject(this)
    }

    @Inject
    protected lateinit var graphicController:GraphicController

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
        return CAMERA_TARGET ?: graphicController.currentPlayerGraphic.getGameImage()
    }

    fun onZoomPlusClicked() {
        currentZoom--
        zoom()
    }

    fun onZoomMinusClicked() {
        currentZoom++
        zoom()
    }

    override fun clear() {
        super.clear()
//        boxStore.close()
//        BoxStore.deleteAllFiles(File(BoxStoreBuilder.DEFAULT_NAME))
    }
}
