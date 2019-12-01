package de.bitb.spacerace.usecase.game.init

import de.bitb.spacerace.core.controller.EditorController
import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Single
import javax.inject.Inject

class LoadEditorUsecase
@Inject constructor(
        private val editorController: EditorController,
        private val mapDataSource: MapDataSource
) : ResultUseCase<MapData, String> {

    override fun buildUseCaseSingle(params: String): Single<MapData> =
            loadMap(params)
                    .doOnSuccess { editorController.initEditor(it) }

    private fun loadMap(name: String): Single<MapData> =
            mapDataSource.getRXMaps(name)
                    .map {
                        if (it.isEmpty()) MapData(name)
                        else it.first()
                    }

}
