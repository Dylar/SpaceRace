package de.bitb.spacerace.ui.screens.editor

import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.core.controller.EditorGloc
import de.bitb.spacerace.ui.screens.game.control.DebugGui
import javax.inject.Inject

class MapStage(
        val screen: EditorScreen
) : BaseStage() {

    @Inject
    protected lateinit var editorGloc: EditorGloc

    init {
        MainGame.appComponent.inject(this)
        addEntitiesToMap()
    }

    fun addEntitiesToMap() {
        addActor(editorGloc.connectionGraphics)
        editorGloc.connectionGraphics.zIndex = 0
        editorGloc.connectionGraphics.forEach {

            if (!it.spaceField1.getGameImage().hasParent()) {
                addActor(it.spaceField1.getGameImage())
            }//TODO add fields undmach die andren

            if (!it.spaceField2.getGameImage().hasParent()) {
                addActor(it.spaceField2.getGameImage())
            }
        }
    }

}