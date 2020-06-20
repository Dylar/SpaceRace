package de.bitb.spacerace.ui.screens.editor

import com.badlogic.gdx.scenes.scene2d.Stage
import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.CameraRenderer
import de.bitb.spacerace.base.CameraStateRenderer
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.EditorGloc
import de.bitb.spacerace.ui.screens.GuiBackstack
import de.bitb.spacerace.ui.screens.GuiBackstackHandler
import de.bitb.spacerace.ui.screens.BackgroundStage
import de.bitb.spacerace.ui.screens.game.control.DebugGui
import javax.inject.Inject

class EditorScreen(
        previous: BaseScreen
) : BaseScreen(previous),
        GuiBackstack by GuiBackstackHandler,
        CameraRenderer by CameraStateRenderer() {

    @Inject
    protected lateinit var editorGloc: EditorGloc

    override lateinit var allStages: List<Stage>
    override lateinit var inputStages: List<Stage>

    override fun show() {
        MainGame.appComponent.inject(this)
        initScreen()
        super.show()
    }

    private fun initScreen() {
        val guiStage = MapGuiStage(this)
        val mapStage = MapStage(this)
        val backgroundStage = BackgroundStage(this) //TODO set background editable
        allStages = listOf(backgroundStage, mapStage, guiStage)
        inputStages = listOf(guiStage, mapStage)

        guiStage.addActor(DebugGui(this)) //TODO just debuging

        initCamera(
                baseScreen = this,
                entityStage = mapStage,
                backgroundStage = backgroundStage,
                centerOnEntity = editorGloc.connectionGraphics.firstOrNull()?.spaceField1?.fieldImage
        )
    }

    override fun hide() {
        super.hide()
        editorGloc.clear()
//        EventBus.getDefault().unregister(this)
    }

}
