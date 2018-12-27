package de.bitb.spacerace.controller

import de.bitb.spacerace.Logger
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerData

class PhaseController() {

    fun openShop() {
        Logger.println("Open shop")
    }

    private fun canEndMain1(playerData: PlayerData): Boolean {
        return playerData.phase.isMain1() && playerData.diced
    }

    private fun canEndMove(playerData: PlayerData): Boolean {
        return !playerData.canMove()
    }

    private fun canEndMain2(playerData: PlayerData): Boolean {
        return playerData.phase.isMain2()
    }

    fun canContinue(playerData: PlayerData): Boolean {
        return when (playerData.phase) {
            Phase.MAIN1 -> canEndMain1(playerData)
            Phase.MOVE -> canEndMove(playerData)
            Phase.MAIN2 -> canEndMain2(playerData)
            Phase.END_TURN -> playerData.phase.isEndTurn()
            Phase.END_ROUND -> playerData.phase.isEndRound()
        }
    }

}