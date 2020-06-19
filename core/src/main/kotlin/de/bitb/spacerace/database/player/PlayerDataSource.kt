package de.bitb.spacerace.database.player

import de.bitb.spacerace.grafik.model.player.PlayerColor
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface PlayerDataSource {

    fun insertRXPlayer(vararg userData: PlayerData): Completable
    fun insertAndReturnRXPlayer(vararg userData: PlayerData): Single<List<PlayerData>>
    fun replaceRXAllPlayer(vararg userData: PlayerData): Single<List<PlayerData>>

    fun deleteRXPlayer(vararg userData: PlayerData): Completable
    fun deleteRXAllPlayer(): Completable

    fun getDBPlayerByColor(vararg color: PlayerColor): List<PlayerData>
    fun getRXAllPlayer(): Single<List<PlayerData>>
    fun getRXPlayerById(vararg userIds: Long): Single<List<PlayerData>>
    fun getRXPlayerByColor(vararg color: PlayerColor): Single<List<PlayerData>>

    fun observeAllPlayer(): Observable<List<PlayerData>>
    fun observePlayerByColor(color: PlayerColor): Observable<List<PlayerData>>


}
