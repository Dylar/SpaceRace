//package de.bitb.spacerace.database
//
//import androidx.room.*
//import de.bitb.spacerace.model.player.PlayerColor
//import de.bitb.spacerace.model.player.PlayerData
//import io.reactivex.Completable
//import io.reactivex.Flowable
//import io.reactivex.Observable
//import io.reactivex.Single
//
//@Dao
//interface PlayerDAO {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(vararg userData: PlayerData): Completable
//
//    @Delete
//    fun delete(vararg userData: PlayerData): Completable
//
////    @Query("DELETE FROM player WHERE color = :firstName AND last_name = :lastName")
////    fun deleteByName(firstName: PlayerColor, lastName: String): Int
//
//    @Query("SELECT * FROM player")
//    fun getAll(): Single<List<PlayerData>>
//
//    @Query("SELECT * FROM player WHERE uuid IN (:userIds)")
//    fun getAllByIds(vararg userIds: String): Single<List<PlayerData>>
//
//    @Query("SELECT * FROM player")
//    fun observeAllFlowable(): Flowable<List<PlayerData>>
//
//    @Query("SELECT * FROM player")
//    fun observeAllObserver(): Observable<List<PlayerData>>
//
//    @Query("SELECT * FROM player WHERE color = :color")
//    fun observeByName(color: PlayerColor): Flowable<List<PlayerData>>
//
//
////    @Query("SELECT * FROM LessonData")
////    fun lessons(): Flowable<List<Player>>
//
//}