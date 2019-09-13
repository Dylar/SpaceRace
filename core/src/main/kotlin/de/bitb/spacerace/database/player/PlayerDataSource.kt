package de.bitb.spacerace.database.player

import de.bitb.spacerace.model.player.PlayerColor
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface PlayerDataSource {

    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg userData: PlayerData): Completable

    fun insertAllReturnAll(vararg userData: PlayerData): Single<List<PlayerData>>

    fun replaceAll(vararg userData: PlayerData): Single<List<PlayerData>>

    //    @Delete
    fun delete(vararg userData: PlayerData): Completable
    fun deleteAll(): Completable

//    @Query("DELETE FROM player WHERE color = :firstName AND last_name = :lastName")
//    fun deleteByName(firstName: PlayerColor, lastName: String): Int

    //    @Query("SELECT * FROM player")
    fun getAll(): Single<List<PlayerData>>

    //    @Query("SELECT * FROM player WHERE uuid IN (:userIds)")
    fun getById(vararg userIds: Long): Single<List<PlayerData>>

    fun getByColor(vararg color: PlayerColor): Single<List<PlayerData>>

    //    @Query("SELECT * FROM player")
    fun observeAllObserver(): Observable<List<PlayerData>>

    //    @Query("SELECT * FROM player WHERE color = :color")
    fun observeByColor(color: PlayerColor): Observable<List<PlayerData>>

    fun getDBByColor(vararg color: PlayerColor): List<PlayerData>
//    fun observeByVictories(amount: Long): Observable<PlayerData>

//    @Query("SELECT * FROM LessonData")
//    fun lessons(): Flowable<List<Player>>

}
