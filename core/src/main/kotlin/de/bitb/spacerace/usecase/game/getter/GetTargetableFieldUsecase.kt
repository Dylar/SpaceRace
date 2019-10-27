package de.bitb.spacerace.usecase.game.getter

import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapDataSource
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.usecase.ResultUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetTargetableFieldUsecase @Inject constructor(
        private val mapDataSource: MapDataSource
) : ResultUseCase<List<FieldData>, PlayerData> {

    override fun buildUseCaseSingle(params: PlayerData): Single<List<FieldData>> =
            mapDataSource
                    .getRXAllFields()
                    .map { fields ->
                        fields.filter { params.canPlayerMoveTo(it) }
                    }
}