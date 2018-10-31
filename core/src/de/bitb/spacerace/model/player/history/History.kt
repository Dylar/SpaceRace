package de.bitb.spacerace.model.player.history

import de.bitb.spacerace.model.player.Ship
import de.bitb.spacerace.model.space.SpaceField

class History {

    val rounds: MutableList<Round> = ArrayList()
    var currentRound: Round = Round(Ship())
        get() = rounds[rounds.size - 1]

    fun nextRound(ship: Ship) {
        rounds.add(Round(ship))
    }

    fun nextPlayer(ship: Ship) {
        currentRound.nextPlayer(ship)
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