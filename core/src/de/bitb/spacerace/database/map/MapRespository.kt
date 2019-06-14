package de.bitb.spacerace.database.map

import de.bitb.spacerace.model.enums.FieldType
import de.bitb.spacerace.model.player.PlayerData
import io.objectbox.Box
import io.objectbox.rx.RxQuery
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class MapRespository(
        private val fieldBox: Box<FieldData>
//        private val posBox: Box<PositionData>
//        private val mapBox: Box<MapData>
) : MapDataSource {

    override fun insertAll(vararg field: FieldData): Single<List<FieldData>> =
            Completable
                    .fromCallable { fieldBox.put(*field) }
                    .andThen(getAllFields(*field))

    override fun getAllFields(vararg field: FieldData): Single<List<FieldData>> =
            RxQuery.single(fieldBox.query()
                    .filter { field.contains(it) }
                    .build())

    override fun delete(vararg field: FieldData): Completable =
            Completable
                    .fromCallable { fieldBox.remove(*field) }

    override fun getPlayerField(vararg player: PlayerData): Single<List<FieldData>> =
            RxQuery.single(fieldBox.query()
//                    .filter { field -> field.players.any { player.contains(it) } }
                    .build())

    override fun observeAllObserver(): Observable<List<FieldData>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun observeByType(type: FieldType): Observable<List<FieldData>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override fun insertAll(vararg userData: PlayerData): Single<List<PlayerData>> {
//        return Completable
//                .fromCallable {
//                    playerBox.put(*userData)
//                }
//                .andThen(getAllBy(*userData))
//    }
//
//    private fun getAllBy(vararg playerData: PlayerData): Single<List<PlayerData>>? {
//        return getByColor(*playerData.map { it.playerColor }.toTypedArray())
//    }
//
//    override fun delete(vararg userData: PlayerData): Completable {
//        return Completable.create { playerBox.remove(*userData) }
//    }
//
//    override fun getAll(): Single<List<PlayerData>> {
//        return RxQuery.single(playerBox.query().build())
//    }
//
//    override fun getById(vararg userIds: Long): Single<List<PlayerData>> {
//        val query = playerBox.query()
//                .filter { userIds.toList().contains(it.uuid) }
//                .build()
//        return RxQuery.single(query)
//    }
//
//    override fun getByColor(vararg color: PlayerColor): Single<List<PlayerData>> {
//        val query = playerBox.query()
//                .filter { color.toList().contains(it.playerColor) }
//                .build()
//        return RxQuery.single(query)
//    }
//
//    override fun observeAllObserver(): Observable<List<PlayerData>> {
//        return RxQuery.observable(playerBox.query().build())
//    }
//
//    override fun observeByColor(color: PlayerColor): Observable<List<PlayerData>> {
//        val query = playerBox.query()
//                .equal(PlayerData_.playerColor, color.toString())
//                .build()
//        return RxQuery.observable(query)
//    }

//    override fun observeByVictories(amount: Long): Observable<PlayerData> {
//        val query = playerBox.query()
//                .equal(PlayerData_.victories, amount)
//                .build()
//        return RxQuery.observable(query).map { it.first() }
//    }
}