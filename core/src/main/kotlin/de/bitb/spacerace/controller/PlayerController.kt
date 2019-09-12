package de.bitb.spacerace.controller

import de.bitb.spacerace.core.PlayerColorDispenser
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.usecase.game.observe.ObserveCurrentPlayerUseCase
import de.bitb.spacerace.utils.Logger
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerController
@Inject constructor(
        private val playerColorDispenser: PlayerColorDispenser,
        private val observeCurrentPlayerUseCase: ObserveCurrentPlayerUseCase
) {

    private var dispo: Disposable? = null

    private val players: MutableList<PlayerColor> = ArrayList()

    var currentPlayerData = NONE_PLAYER_DATA
    val currentColor: PlayerColor
        get() = players.firstOrNull() ?: PlayerColor.NONE

    fun initObserver() {
        dispo?.dispose()
        dispo = observeCurrentPlayerUseCase.observeStream(
                onNext = {
                    currentPlayerData = it
                })
    }

    fun changePlayer() {
        val oldPlayer = currentColor
        players.removeAt(0)
        players.add(oldPlayer)
        Logger.println("newPlayer: $currentColor")
        playerColorDispenser.publishUpdate(currentColor)
    }

    fun addPlayer(playerColor: PlayerColor) {
        players.add(playerColor)
    }

    fun clear() = dispo?.dispose()
}