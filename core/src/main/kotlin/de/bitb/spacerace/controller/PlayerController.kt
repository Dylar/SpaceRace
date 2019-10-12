package de.bitb.spacerace.controller

import de.bitb.spacerace.core.PlayerColorDispenser
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.database.player.PlayerDataSource
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import de.bitb.spacerace.utils.Logger
import io.objectbox.Box
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerController
@Inject constructor(
        private val playerColorDispenser: PlayerColorDispenser,
        private val observeCurrentPlayerUseCase: ObserveCurrentPlayerUseCase,
        private val playerDataSource: PlayerDataSource
) {

    private var dispo: Disposable? = null

    val players: MutableList<PlayerColor> = ArrayList()
    var currentPlayerIndex = 0

    val currentColor: PlayerColor
        get() = players[currentPlayerIndex]
    val currentPlayerData
        get() = playerDataSource.getDBByColor(currentColor).first()

    fun initObserver() {
        dispo?.dispose()
        dispo = observeCurrentPlayerUseCase.observeStream(
                onNext = {
//                    currentPlayerData = it //TODO ??
                })
    }

    fun getPlayerIndex(playerColor: PlayerColor = currentColor) = players.indexOf(playerColor)

    fun changePlayer() {
        currentPlayerIndex = (getPlayerIndex() + 1) % players.size
        Logger.println("newPlayer: $currentColor")
        playerColorDispenser.publishUpdate(currentColor)
    }

    fun addPlayer(playerColor: PlayerColor) {
        players.add(playerColor)
    }

    fun clear() {
        dispo?.dispose()
        players.clear()
    }

}