package de.bitb.spacerace.model.player

import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.itemtype.DiceAddition
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.space.fields.SpaceField

data class PlayerData(val playerColor: PlayerColor = PlayerColor.NONE) {

    var x = 0f
        get() = fieldPosition.getAbsolutX()
    var y = 0f
        get() = fieldPosition.getAbsolutY()

    var credits = 0
    var items = ArrayList<Item>()

    var diceModItems = ArrayList<DiceModification>()
    var diceAddItems = ArrayList<DiceAddition>()

    var diced = false
    var diceResult: Int = 0

    var phase: Phase = Phase.MAIN1

    var fieldPosition: SpaceField = SpaceField.NONE
    var steps: MutableList<SpaceField> = ArrayList()
    var previousStep: SpaceField = SpaceField.NONE
        get() = if (steps.size < 2) SpaceField.NONE else steps[steps.size - 2]

    fun getMaxMoving(): Int {
        var add = 0
        var mod = 1f
        for (diceModItem in diceModItems) {
            mod += diceModItem.getModification()
        }
        for (diceAddItem in diceAddItems) {
            add += diceAddItem.getAddition()
        }
        return (diceResult * mod + add).toInt()
    }

    fun stepsLeft(): Int {
        return getMaxMoving() - (steps.size - 1)
    }

    internal fun canMove(): Boolean {
        return phase.isMoving() && stepsLeft() > 0
    }

    fun getUsedItems(): List<Item> {
        val result = ArrayList<Item>()
        for (diceModItem in diceModItems) {
            result.add(diceModItem as Item)
        }
        for (diceAddItem in diceAddItems) {
            result.add(diceAddItem as Item)
        }
        return result
    }

}