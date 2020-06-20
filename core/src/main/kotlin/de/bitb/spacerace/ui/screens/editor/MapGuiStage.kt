package de.bitb.spacerace.ui.screens.editor

import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.ui.base.GuiBuilder
import de.bitb.spacerace.ui.base.SRAlign
import de.bitb.spacerace.ui.base.SRBackgroundWrapper

class MapGuiStage(
        val screen: EditorScreen
) : BaseStage(), GuiBuilder {

    private val modeGui: SRMapSelectModeGui = SRMapSelectModeGui()
    private val modeGuiWrapper = SRBackgroundWrapper(modeGui)

    private val selectEntityGui: SRSelectEntityGui = SRSelectEntityGui()

    init {
        initContent()
    }

    private fun initContent() {
        addSelectModeGui()
        addSelectEntityGui()

    }

    private fun addSelectModeGui() {
        addActor(modeGuiWrapper)
        modeGuiWrapper.alignGui(
                guiPosX = 0f,
                guiPosY = 0f,
                guiWidth = modeGuiWrapper.width,
                guiHeight = modeGuiWrapper.height,
                alignHoriz = SRAlign.LEFT,
                alignVert = SRAlign.BOTTOM)
    }

    private fun addSelectEntityGui() {
        addActor(selectEntityGui)
        selectEntityGui.alignGui(
                guiPosX = Dimensions.SCREEN_WIDTH,
                guiPosY = Dimensions.SCREEN_HEIGHT,
                guiWidth = selectEntityGui.width,
                guiHeight = selectEntityGui.height,
                alignHoriz = SRAlign.RIGHT,
                alignVert = SRAlign.TOP)
    }

//    override fun act(delta: Float) {
//        when {
//            Gdx.input.isKeyJustPressed(Input.Keys.SPACE) -> centerCamera()
//            Gdx.input.isKeyJustPressed(Input.Keys.SLASH) -> screen.onZoomMinusClicked()
//            Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET) -> screen.onZoomPlusClicked()
//        }
//        super.act(delta)
//    }

//    private fun centerCamera() {
//        val cameraAction = screen.cameraState
//        val target = if (cameraAction is CameraState.FREE) screen.cameraTarget else null
//        screen.centerCamera(target)
//        srViewControlGui.updateButtons(cameraAction)
//    }

}