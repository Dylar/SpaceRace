package de.bitb.spacerace.database

import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerData
import io.objectbox.Box
import io.objectbox.rx.RxQuery
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class PlayerRespository(
        private val playerBox: Box<PlayerData>
) : PlayerDataSource {

    override fun insertAll(vararg userData: PlayerData): Single<List<PlayerData>> {
        return Completable
                .fromCallable {
                    playerBox.put(*userData)
                }
                .andThen(getAllBy(*userData))
    }

    private fun getAllBy(vararg playerData: PlayerData): Single<List<PlayerData>>? {
        return getByColor(*playerData.map { it.playerColor }.toTypedArray())
    }

    override fun delete(vararg userData: PlayerData): Completable {
        return Completable.create { playerBox.remove(*userData) }
    }

    override fun getAll(): Single<List<PlayerData>> {
        return RxQuery.single(playerBox.query().build())
    }

    override fun getById(vararg userIds: Long): Single<List<PlayerData>> {
        val query = playerBox.query()
                .filter { userIds.toList().contains(it.uuid) }
                .build()
        return RxQuery.single(query)
    }

    override fun getByColor(vararg color: PlayerColor): Single<List<PlayerData>> {
        val query = playerBox.query()
                .filter { color.toList().contains(it.playerColor) }
                .build()
        return RxQuery.single(query)
    }

    override fun observeAllObserver(): Observable<List<PlayerData>> {
        return RxQuery.observable(playerBox.query().build())
    }

    override fun observeByColor(color: PlayerColor): Observable<List<PlayerData>> {
        val query = playerBox.query()
//                .equal(PlayerData_.playerColor, color.toString())
                .build()
        return RxQuery.observable(query)
    }

//    override fun observeByVictories(amount: Long): Observable<PlayerData> {
//        val query = playerBox.query()
//                .equal(PlayerData_.victories, amount)
//                .build()
//        return RxQuery.observable(query).map { it.first() }
//    }
}