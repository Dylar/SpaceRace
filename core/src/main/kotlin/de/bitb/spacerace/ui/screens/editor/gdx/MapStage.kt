package de.bitb.spacerace.ui.screens.editor.gdx

import com.badlogic.gdx.math.Vector3
import de.bitb.spacerace.base.BaseStage
import de.bitb.spacerace.config.dimensions.Dimensions
import de.bitb.spacerace.core.MainGame
import de.bitb.spacerace.grafik.model.space.groups.ConnectionList
import de.bitb.spacerace.ui.screens.editor.AddEntityToMap
import de.bitb.spacerace.ui.screens.editor.EditorGloc
import javax.inject.Inject

class MapStage(
        val screen: EditorScreen
) : BaseStage() {

    @Inject
    protected lateinit var editorGloc: EditorGloc

    init {
        MainGame.appComponent.inject(this)
        observeAddEntity()
    }

    private fun observeAddEntity() {
        editorGloc.observeAddEntityToMapUseCase.observeStream { addEntity ->
            when (addEntity) {
                is AddEntityToMap.AddFieldToMap -> {
                    val screenCenter = Vector3(Dimensions.SCREEN_WIDTH / 2, (Dimensions.SCREEN_HEIGHT / 2), 0f);
                    val centerPoint = camera.unproject(screenCenter)

                    addEntity.fieldGraphic.setPosition(centerPoint.x, centerPoint.y)
                    addActor(addEntity.fieldGraphic.getGameImage())
                }
                is AddEntityToMap.AddConnectionsToMap -> {
                    val connectionList = addEntity.connectionList
                    addActor(connectionList)
                }
            }

        }.addDisposable()
    }
}