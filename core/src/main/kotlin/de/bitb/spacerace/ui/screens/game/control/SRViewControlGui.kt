package de.bitb.spacerace.ui.screens.game.control

import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import de.bitb.spacerace.CameraActions
import de.bitb.spacerace.config.DEBUG_LAYOUT
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_DOWN
import de.bitb.spacerace.grafik.IMAGE_PATH_BUTTON_UP
import de.bitb.spacerace.grafik.IMAGE_PATH_GUI_BACKGROUND
import de.bitb.spacerace.grafik.TexturePool
import de.bitb.spacerace.ui.base.GuiBuilder
import de.bitb.spacerace.ui.base.SRAlign
import de.bitb.spacerace.ui.screens.game.GameScreen
import javax.inject.Inject

class SRViewControlGui(
        screen: GameScreen
) : VisTable(), GuiBuilder {

    @Inject
    protected lateinit var playerController: PlayerController
    private lateinit var lockBtn: VisTextButton

    init {
        MainGame.appComponent.inject(this)
        debug = DEBUG_LAYOUT

        setContent(screen)
        setDimensions()
        setBackgroundByPath(IMAGE_PATH_GUI_BACKGROUND)
        pack()
        scaleTable(.9f, 0.7f)
    }


    private fun setDimensions() {
        alignGui(
                guiWidth = prefWidth,
                guiHeight = prefHeight,
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
                    screen.centerCamera()
                    updateButtons(screen)
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
                    imageUp = TexturePool.getSmallButton(IMAGE_PATH_BUTTON_UP),
                    imageDown = TexturePool.getSmallButton(IMAGE_PATH_BUTTON_DOWN))
                    .also { add(it).pad(4f) }

    fun updateButtons(screen: GameScreen) {
        lockBtn.setText(when {
            screen.cameraStatus == CameraActions.CAMERA_FREE -> "(O)"
            else -> "(X)"
        })
    }

}