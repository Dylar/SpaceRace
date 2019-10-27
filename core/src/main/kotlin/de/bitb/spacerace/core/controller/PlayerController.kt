package de.bitb.spacerace.core.controller

import de.bitb.spacerace.usecase.dispender.PlayerColorDispenser
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import de.bitb.spacerace.core.utils.Logger
import io.objectbox.Box
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerController
@Inject constructor(
        private val playerColorDispenser: PlayerColorDispenser,
        private val observeCurrentPlayerUseCase: ObserveCurrentPlayerUseCase,
        val box: Box<PlayerData>
) {

    private var dispo: Disposable? = null

    val players: MutableList<PlayerColor> = ArrayList()
    var currentPlayerIndex = 0

    var currentPlayerData = NONE_PLAYER_DATA
    val currentColor: PlayerColor
        get() = players[currentPlayerIndex]

    fun initObserver() {
        dispo?.dispose()
        dispo = observeCurrentPlayerUseCase.observeStream(
                onNext = {
                    currentPlayerData = it
                })
    }

    fun getPlayerIndex(playerColor: PlayerColor = currentColor) = players.indexOf(playerColor)

    fun changePlayer() {
        currentPlayerIndex = (getPlayerIndex() + 1) % players.size
        Logger.printLog("newPlayer: $currentColor")
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