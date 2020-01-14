package de.bitb.spacerace.ui.screens.editor

import de.bitb.spacerace.base.BaseStage

class MapGuiStage(
       val screen: EditorScreen
) : BaseStage() {

    private var modeGui: SRMapSelectModeGui = SRMapSelectModeGui()

    init {
        addActor(modeGui)
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