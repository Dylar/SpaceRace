package de.bitb.spacerace.usecase.game.getter

import de.bitb.spacerace.database.map.MapData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.usecase.ResultUseCase
import de.bitb.spacerace.usecase.ResultUseCaseNoParams
import io.reactivex.Single
import javax.inject.Inject

class GetMapUsecase @Inject constructor(
        private val mapDataSource: MapDataSource
) : ResultUseCaseNoParams<MapData> {

    override fun buildUseCaseSingle(): Single<MapData> {
        return mapDataSource
                .getMap()
    }
}