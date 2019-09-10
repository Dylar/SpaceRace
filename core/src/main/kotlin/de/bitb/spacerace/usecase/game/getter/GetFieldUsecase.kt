package de.bitb.spacerace.usecase.game.getter

import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetFieldUsecase @Inject constructor(
        private val mapDataSource: MapDataSource
) : ResultUseCase<FieldData, PositionData> {

    override fun buildUseCaseSingle(params: PositionData): Single<FieldData> {
        return mapDataSource
                .getField(params)
    }
}