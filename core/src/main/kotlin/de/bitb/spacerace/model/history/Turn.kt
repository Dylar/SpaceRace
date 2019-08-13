package de.bitb.spacerace.model.history

import de.bitb.spacerace.model.player.Player
import de.bitb.spacerace.model.space.fields.SpaceField

class Turn(val player: Player) {

    val activities: MutableList<Activity> = ArrayList()
    lateinit var steps: MutableList<SpaceField>

    fun addActivity(activity: Activity) {
        activity.turn = this
        activities.add(activity)
    }
}