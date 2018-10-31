package de.bitb.spacerace.model.player.history

import de.bitb.spacerace.model.player.Ship
import de.bitb.spacerace.model.space.SpaceField

class Turn(val ship: Ship) {

    val activities: MutableList<Activity> = ArrayList()
    lateinit var steps: MutableList<SpaceField>

    fun addActivity(activity: Activity) {
        activity.turn = this
        activities.add(activity)
    }
}