package de.bitb.spacerace.database.items

import de.bitb.spacerace.database.player.PlayerData


fun PlayerData.getModifierValues(): Pair<Double, Int> {
    var modifierValue = 0.0
    var additionValue = 0
    fun addValues(items: List<ItemData>) {
        items.asSequence()
                .map { it.itemInfo }
                .forEach {
                    when (it) {
                        is DiceModification -> modifierValue += it.diceModifier
                        is DiceAddition -> additionValue += it.diceAddition
                    }
                }
    }
    addValues(equippedItems)
    addValues(attachedItems)

    return Pair(modifierValue, additionValue)
}