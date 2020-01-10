package de.bitb.spacerace.ui.screens.game.control

import com.kotcrab.vis.ui.widget.VisTextButton
import de.bitb.spacerace.base.CameraAction
import de.bitb.spacerace.base.CameraAction.TARGET_ACTION.*
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_HEIGHT_DEFAULT
import de.bitb.spacerace.config.dimensions.Dimensions.GameGuiDimensions.GAME_BUTTON_WIDTH_DEFAULT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_DOWN
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_UP
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.ui.base.SRAlign
import de.bitb.spacerace.ui.base.SRGuiGrid
import de.bitb.spacerace.ui.screens.game.GameScreen
import javax.inject.Inject

class SRViewControlGui(
        screen: GameScreen
) : SRGuiGrid() {

    @Inject
    protected lateinit var playerController: PlayerController
    private lateinit var lockBtn: VisTextButton

    init {
        MainGame.appComponent.inject(this)
        debug = DEBUG_LAYOUT

        setContent(screen)
        setDimensions()
        pack()
    }


    private fun setDimensions() {
        setItemSize(GAME_BUTTON_WIDTH_DEFAULT * .7f, GAME_BUTTON_HEIGHT_DEFAULT * .7f)
        setGuiBorder(
                columns = 1f,
                rows = 3f,
                alignHoriz = SRAlign.LEFT,
                alignVert = SRAlign.BOTTOM)
    }

    private fun setContent(screen: GameScreen) {
        setZoomInBtn(screen)
        setLookBtn(screen)
        setZoomOutBtn(screen)
    }

    private fun setZoomInBtn(screen: GameScreen) {
        createSmallButtons(
                text = "+",
                listener = { screen.onZoomPlusClicked() })

    }

    private fun setLookBtn(screen: GameScreen) {
        lockBtn = createSmallButtons(
                text = "(O)",
                listener = {
                    screen.centerCamera(screen.cameraTarget)
                    updateButtons(screen.cameraAction)
                })
    }

    private fun setZoomOutBtn(screen: GameScreen) {
        createSmallButtons(
                text = "-",
                listener = { screen.onZoomMinusClicked() })
    }

    private fun createSmallButtons(text: String, listener: () -> Unit) =
            createTextButtons(
                    text = text,
                    listener = listener,
                    imageUp = TexturePool.getButton(IMAGE_PATH_BUTTON_UP),
                    imageDown = TexturePool.getButton(IMAGE_PATH_BUTTON_DOWN)
            )
                    .also {
                        addActor(it)
                    }

    fun updateButtons(cameraAction: CameraAction) {
        val text = when (cameraAction) {
            is CAMERA_LOCKED -> "(O)"
            else -> "(X)"
        }
        lockBtn.setText(text)
    }

}