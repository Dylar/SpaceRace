package de.bitb.spacerace.controller

import de.bitb.spacerace.utils.Logger
import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.player.NONE_PLAYER
import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerController
@Inject constructor(
) {

    var currentPlayerData = NONE_PLAYER_DATA

    var players: MutableList<Player> = ArrayList()

    val currentPlayer: Player
        get() = players.firstOrNull() ?: NONE_PLAYER

    fun fixColor(playerData: PlayerData) {
        Logger.println("SET CURRENT: $playerData")
        currentPlayerData = playerData
    }

    fun getPlayer(playerColor: PlayerColor): Player {
        return players.find { playerColor == it.playerColor } ?: NONE_PLAYER
    }

    fun clearPlayer() {
        players.clear()
    }

    fun getMaxSteps(playerData: PlayerData): Int =
            getPlayerItems(playerData.playerColor).getModifierValues(1)
                    .let { (mod, add) ->
                        val diceResult = playerData.diceResults.sum()
                        val result = (diceResult * mod + add).toInt()

                        if (playerData.diceResults.isNotEmpty() && result <= 0) 1 else result
                    }

    private fun getPlayerItems(playerColor: PlayerColor): PlayerItems =
            getPlayer(playerColor).playerItems

    fun stepsLeft(playerData: PlayerData): Int =
            getMaxSteps(playerData) - (if (playerData.steps.isEmpty()) 0 else playerData.steps.size - 1)

    fun areStepsLeft(playerData: PlayerData): Boolean =
            stepsLeft(playerData) > 0

    fun canMove(playerData: PlayerData): Boolean =
            playerData.phase.isMoving() && areStepsLeft(playerData)

    fun addPlayer(player: Player) {
        players.add(player)
    }

}