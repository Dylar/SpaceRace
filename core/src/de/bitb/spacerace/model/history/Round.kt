package de.bitb.spacerace.model.history

import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.player.PlayerColor

class Round(player:Player) {

    val roundActivities: MutableList<Activity> = ArrayList()

    val turns: MutableList<Turn> = ArrayList()
    var currentTurn: Turn = Turn(Player(PlayerColor.NONE))
        get() = turns[turns.size - 1]

    init {
        nextPlayer(player)
    }

    fun nextPlayer(player: Player) {
        turns.add(Turn(player))
    }

    fun addActivity(activity: Activity) {
        currentTurn.addActivity(activity)
    }

    fun addRoundActivity(activity: Activity) {
        roundActivities.add(activity)
    }

}