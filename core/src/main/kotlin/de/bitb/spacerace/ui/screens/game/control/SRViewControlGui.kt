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

class SRViewControlGui(
        screen: GameScreen
) : VisTable(), GuiBuilder {

    @Inject
    protected lateinit var playerController: PlayerController
    private lateinit var lockBtn: VisTextButton

    init {
        MainGame.appComponent.inject(this)

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
        createTextButtons(
                text = "+",
                listener = { screen.onZoomPlusClicked() })
                .also {
                    add(it).pad(4f)
                }
    }

    private fun setLookBtn(screen: GameScreen) {
        lockBtn = createTextButtons(
                text = "(O)",
                listener = {
                    screen.centerCamera()
                    updateButtons(screen)
                })
                .also {
                    add(it).pad(4f)
                }
    }

    private fun setZoomOutBtn(screen: GameScreen) {
        createTextButtons(
                text = "-",
                listener = { screen.onZoomMinusClicked() })
                .also {
                    add(it).pad(4f)
                }
    }

    fun updateButtons(screen: GameScreen) {
        lockBtn.setText(when {
            screen.cameraStatus == CameraActions.CAMERA_FREE -> "(O)"
            else -> "(X)"
        })
    }

}