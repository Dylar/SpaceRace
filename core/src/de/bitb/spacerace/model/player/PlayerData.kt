package de.bitb.spacerace.model.player

import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.items.Item
import de.bitb.spacerace.model.items.ItemCollection
import de.bitb.spacerace.model.items.itemtype.DiceAddition
import de.bitb.spacerace.model.items.itemtype.DiceModification
import de.bitb.spacerace.model.items.upgrade.UpgradeItem
import de.bitb.spacerace.model.space.fields.SpaceField

data class PlayerData(val playerColor: PlayerColor = PlayerColor.NONE) {

    var credits = 100000
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

    fun getMaxSteps(): Int {
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
        return getMaxSteps() - (if (steps.isEmpty()) 0 else steps.size - 1)
    }

    fun canMove(): Boolean {
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

    fun nextRound() {
        steps = ArrayList()
        diceResult = 0
        diced = false
        phase = Phase.MAIN1
    }

    fun removeUsedItems() {
        val usedItems = ArrayList<Item>()
        for (item in items) {
            if (item !is UpgradeItem && item.used)
                usedItems.add(item)
        }
        items.removeAll(usedItems)
    }

    fun getItems(itemType: ItemCollection): List<Item> {
        val list = ArrayList<Item>()
        for (item in items) {
            if (item.itemType == itemType) {
                list.add(item)
            }
        }
        return list
    }

}