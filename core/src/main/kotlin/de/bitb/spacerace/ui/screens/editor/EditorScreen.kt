package de.bitb.spacerace.ui.screens.editor

import de.bitb.spacerace.base.BaseScreen
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.EditorController
import de.bitb.spacerace.ui.screens.game.BackgroundStage
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class EditorScreen(
        game: MainGame,
        previous: BaseScreen
) : BaseScreen(game, previous) {

    @Inject
    protected lateinit var editorController: EditorController

    //    override fun createGuiStage(): BaseStage {
//        return StartGuiStage(this)
//    }
//
    override fun createBackgroundStage(): BaseStage {
        return BackgroundStage(this)
    }

    override fun show() {
        super.show()
        MainGame.appComponent.inject(this)
        addEntities()
    }

    private fun addEntities() {
        gameStage.clear()

        gameStage.addActor(editorController.connectionGraphics)
        editorController.connectionGraphics.zIndex = 0
        editorController.connectionGraphics.forEach {

            if(!it.spaceField1.getGameImage().hasParent()){
                gameStage.addActor(it.spaceField1.getGameImage())
            }//TODO add fields undmach die andren

            if(!it.spaceField2.getGameImage().hasParent()){
                gameStage.addActor(it.spaceField2.getGameImage())
            }
        }
    }

    override fun hide() {
        super.hide()
        editorController.clear()
        EventBus.getDefault().unregister(this)
    }

}
