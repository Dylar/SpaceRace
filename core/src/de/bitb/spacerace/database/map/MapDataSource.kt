package de.bitb.spacerace.database.map

import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import de.bitb.spacerace.model.space.fields.SpaceField
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MapDataSource {

    fun insertAll(vararg userData: SpaceField): Single<List<SpaceField>>

    fun delete(vararg userData: PlayerData): Completable

    fun getAllFields(): Single<List<SpaceField>>

    fun getById(vararg userIds: Long): Single<List<SpaceField>>

    fun getByColor(vararg color: PlayerColor): Single<List<SpaceField>>

    fun observeAllObserver(): Observable<List<SpaceField>>

    fun observeByType(color: FieldType): Observable<List<SpaceField>>

}
