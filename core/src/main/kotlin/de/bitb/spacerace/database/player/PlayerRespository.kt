package de.bitb.spacerace.database.player

import de.bitb.spacerace.database.savegame.SaveData
import de.bitb.spacerace.database.savegame.SaveData_
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.toName
import io.objectbox.Box
import io.objectbox.kotlin.inValues
import io.objectbox.rx.RxQuery
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class PlayerRespository(
        private val saveBox: Box<SaveData>,
        private val playerBox: Box<PlayerData>
) : PlayerDataSource {

    private fun getSaveData() = saveBox.query().equal(SaveData_.loaded, true).build().find().firstOrNull()
    private fun getCurrentPlayers() = getSaveData()?.players ?: listOf<PlayerData>()
    private fun getCurrentPlayerIds() = getCurrentPlayers().map { it.uuid }.toLongArray()
    private fun getPlayerId(color: PlayerColor): Long = getCurrentPlayers().find { it.playerColor == color }?.uuid
            ?: error("Color $color not selected")

    override fun getDBPlayerByColor(vararg color: PlayerColor): List<PlayerData> =
            getCurrentPlayers().filter { it.playerColor in color }

    override fun insertRXPlayer(vararg userData: PlayerData): Completable =
            Completable.fromCallable { playerBox.put(*userData) }

    override fun insertAndReturnRXPlayer(vararg userData: PlayerData): Single<List<PlayerData>> =
            insertRXPlayer(*userData)
                    .andThen(getRXPlayerById(*userData.map { it.uuid }.toLongArray()))

    override fun replaceRXAllPlayer(vararg userData: PlayerData): Single<List<PlayerData>> =
            deleteRXPlayer(*userData).andThen(insertAndReturnRXPlayer(*userData))

    override fun deleteRXPlayer(vararg userData: PlayerData): Completable =
            Completable.fromAction { playerBox.remove(*userData) }

    override fun deleteRXAllPlayer(): Completable =
            Completable.fromAction { playerBox.removeAll() }

    override fun getRXAllPlayer(): Single<List<PlayerData>> =
            Single.fromCallable { getCurrentPlayers() }

    override fun getRXPlayerById(vararg userIds: Long): Single<List<PlayerData>> {
        val query = playerBox.query()
                .inValues(PlayerData_.uuid, userIds)
        return RxQuery.single(query.build())
    }

    override fun getRXPlayerByColor(vararg color: PlayerColor): Single<List<PlayerData>> {
        val playerIds = getCurrentPlayerIds()
        val query = playerBox.query()
                .inValues(PlayerData_.uuid, playerIds)
                .inValues(PlayerData_.playerColor, color.toName())
        return RxQuery.single(query.build())
    }

    override fun observeAllPlayer(): Observable<List<PlayerData>> {
        val playerIds = getCurrentPlayerIds()
        val query = playerBox.query()
                .inValues(PlayerData_.uuid, playerIds)
        return RxQuery.observable(query.build())
    }

    override fun observePlayerByColor(color: PlayerColor): Observable<List<PlayerData>> {
        val playerId = getPlayerId(color)
        val query = playerBox.query()
                .equal(PlayerData_.uuid, playerId)
        return RxQuery.observable(query.build())
    }

}