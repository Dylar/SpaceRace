package de.bitb.spacerace.model.player.history

import de.bitb.spacerace.model.player.Ship

class Round(ship:Ship) {

    val roundActivities: MutableList<Activity> = ArrayList()

    val turns: MutableList<Turn> = ArrayList()
    var currentTurn: Turn = Turn(Ship())
        get() = turns[turns.size - 1]

    init {
        nextPlayer(ship)
    }

    fun nextPlayer(ship: Ship) {
        turns.add(Turn(ship))
    }

    fun addActivity(activity: Activity) {
        currentTurn.addActivity(activity)
    }

    fun addRoundActivity(activity: Activity) {
        roundActivities.add(activity)
    }

}