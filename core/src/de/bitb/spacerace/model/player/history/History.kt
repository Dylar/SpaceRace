package de.bitb.spacerace.model.player.history

import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.SpaceField

class History {

    val rounds: MutableList<Round> = ArrayList()
    var currentRound: Round = Round(Player())
        get() = rounds[rounds.size - 1]

    fun nextRound(player: Player) {
        rounds.add(Round(player))
    }

    fun nextPlayer(player: Player) {
        currentRound.nextPlayer(player)
    }

    fun addActivity(activity: Activity) {
        currentRound.addActivity(activity)
    }

    fun setSteps(steps: MutableList<SpaceField>) {
        currentRound.currentTurn.steps = steps
    }

    fun addRoundActivity(activity: Activity) {
        currentRound.addRoundActivity(activity)
    }
}