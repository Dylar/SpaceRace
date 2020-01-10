package de.bitb.spacerace.ui.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import de.bitb.spacerace.base.BaseGuiStage
import de.bitb.spacerace.base.CameraAction
import de.bitb.spacerace.base.CameraRenderer
import de.bitb.spacerace.ui.screens.game.player.SRPlayerStatsGui
import de.bitb.spacerace.ui.screens.game.control.SRActionGui
import de.bitb.spacerace.ui.screens.game.control.SRViewControlGui


class GameGuiStage(
        screen: GameScreen
) : BaseGuiStage(screen) {

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
            Gdx.input.isKeyJustPressed(Input.Keys.SPACE) -> {
                if (screen is GameScreen) {
                    val cameraAction = screen.cameraAction
                    val target = if (cameraAction is CameraAction.CAMERA_FREE) screen.cameraTarget else null
                    screen.centerCamera(target)
                    srViewControlGui.updateButtons(cameraAction)
                }
            }
            Gdx.input.isKeyJustPressed(Input.Keys.SLASH) -> (screen as GameScreen).onZoomMinusClicked()
            Gdx.input.isKeyJustPressed(Input.Keys.RIGHT_BRACKET) -> (screen as GameScreen).onZoomPlusClicked()
        }
        super.act(delta)
    }

}