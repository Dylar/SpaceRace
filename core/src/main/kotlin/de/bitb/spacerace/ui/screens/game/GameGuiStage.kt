package de.bitb.spacerace.ui.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.base.CameraAction
import de.bitb.spacerace.ui.screens.game.player.SRPlayerStatsGui
import de.bitb.spacerace.ui.screens.game.control.SRActionGui
import de.bitb.spacerace.ui.screens.game.control.SRViewControlGui


class GameGuiStage(
       val screen: GameScreen
) : BaseStage() {

    private var srActionGui: SRActionGui = SRActionGui()
    private var srPlayerStatsGui: SRPlayerStatsGui = SRPlayerStatsGui()
    private var srViewControlGui: SRViewControlGui = SRViewControlGui(screen)

    init {
        addActor(srActionGui)
        addActor(srPlayerStatsGui)
        addActor(srViewControlGui)
    }

    override fun act(delta: Float) {
        when {
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE) -> centerCamera()
            Gdx.input.isKeyJustPressed(Input.Keys.SLASH) -> screen.onZoomMinusClicked()
            Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET) -> screen.onZoomPlusClicked()
        }
        super.act(delta)
    }

    private fun centerCamera() {
        val cameraAction = screen.cameraAction
        val target = if (cameraAction is CameraAction.CAMERA_FREE) screen.cameraTarget else null
        screen.centerCamera(target)
        srViewControlGui.updateButtons(cameraAction)
    }

}