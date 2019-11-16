package de.bitb.spacerace.ui.screens.game.control

import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import de.bitb.spacerace.CameraActions
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.PlayerController
import de.bitb.spacerace.grafik.IMAGE_PATH_GUI_BACKGROUND
import de.bitb.spacerace.ui.base.GuiBuilder
import de.bitb.spacerace.ui.base.SRAlign
import de.bitb.spacerace.ui.screens.game.GameScreen
import javax.inject.Inject

class SRViewControlGui(screen: GameScreen) : VisTable(), GuiBuilder {

    @Inject
    protected lateinit var playerController: PlayerController
    private lateinit var lockBtn: VisTextButton

    init {
        MainGame.appComponent.inject(this)

        setBackgroundByPath(IMAGE_PATH_GUI_BACKGROUND)
        setContent(screen)
        setDimensions()
        debug = true
    }

    private fun setDimensions() {
        alignGui(
                guiPosX = 100f,
                guiPosY = 100f,
                guiWidth = 100f,
                guiHeight = 100f,
                alignHoriz = SRAlign.LEFT,
                alignVert = SRAlign.BOTTOM)
    }

    private fun setContent(screen: GameScreen) {
        setZoomInBtn(screen)
        setLookBtn(screen)
        setZoomOutBtn(screen)
    }

    private fun setZoomInBtn(screen: GameScreen) {
        createTextButtons(
                text = "+",
                listener = { screen.onZoomPlusClicked() })
                .also {
                    add(it)
                }
    }

    private fun setLookBtn(screen: GameScreen) {
        lockBtn = createTextButtons(
                text = "O",
                listener = {
                    screen.centerCamera()
                    updateButtons(screen)
                })
                .also {
                    add(it)
                }
    }

    private fun setZoomOutBtn(screen: GameScreen) {
        createTextButtons(
                text = "-",
                listener = { screen.onZoomMinusClicked() })
                .also {
                    add(it)
                }
    }

    fun updateButtons(screen: GameScreen) {
        lockBtn.setText(when {
            screen.cameraStatus == CameraActions.CAMERA_FREE -> "(O)"
            else -> "(X)"
        })
    }

}