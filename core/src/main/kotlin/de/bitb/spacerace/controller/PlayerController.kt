package de.bitb.spacerace.controller

import de.bitb.spacerace.database.player.NONE_PLAYER_DATA
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.player.PlayerItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerController
@Inject constructor(
        private val graphicController: GraphicController
) {

    var currentPlayerData = NONE_PLAYER_DATA

    fun getMaxSteps(playerData: PlayerData): Int =
            getPlayerItems(playerData.playerColor).getModifierValues(1)
                    .let { (mod, add) ->
                        val diceResult = playerData.diceResults.sum()
                        val result = (diceResult * mod + add).toInt()

                        if (playerData.diceResults.isNotEmpty() && result <= 0) 1 else result
                    }

    private fun getPlayerItems(playerColor: PlayerColor): PlayerItems =
            graphicController.getPlayer(playerColor).playerItems

    fun stepsLeft(playerData: PlayerData): Int =
            getMaxSteps(playerData) - (if (playerData.steps.isEmpty()) 0 else playerData.steps.size - 1)

    fun areStepsLeft(playerData: PlayerData): Boolean =
            stepsLeft(playerData) > 0

    fun canMove(playerData: PlayerData): Boolean =
            playerData.phase.isMoving() && areStepsLeft(playerData)

}