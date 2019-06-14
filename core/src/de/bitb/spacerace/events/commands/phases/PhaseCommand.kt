package de.bitb.spacerace.events.commands.phases

import de.bitb.spacerace.events.commands.BaseCommand
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.database.player.PlayerData

abstract class PhaseCommand(playerColor: PlayerColor) : BaseCommand(playerColor) {

    private fun canEndMain1(playerData: PlayerData): Boolean {
        return playerData.phase.isMain1() && playerData.areStepsLeft()
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