package de.bitb.spacerace.model.player

import de.bitb.spacerace.base.PlayerColor
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.space.fields.SpaceField

data class PlayerData(val playerColor: PlayerColor = PlayerColor.NONE) {

    var x = 0f
        get() = fieldPosition.x
    var y = 0f
        get() = fieldPosition.y

    var credits = 0
    var items = ArrayList<Item>()

    var phase: Phase = Phase.MAIN1

    var diced = false
    var diceResult: Int = 0

    var steps: MutableList<SpaceField> = ArrayList()
    var previousStep: SpaceField = SpaceField.NONE
        get() = if (steps.size < 2) SpaceField.NONE else steps[steps.size - 2]

    lateinit var fieldPosition: SpaceField

    fun stepsLeft(): Int {
        return diceResult - (steps.size - 1)
    }

    internal fun canMove(): Boolean {
        return phase.isMoving() && stepsLeft() > 0
    }

}