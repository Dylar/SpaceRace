package de.bitb.spacerace.database.items

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.items.itemtype.DiceAddition
import de.bitb.spacerace.model.items.itemtype.DiceModification


fun PlayerData.getModifierValues(modModifier: Int = 0): Pair<Double, Int> {
    var modifierValue = 0.0
    var additionValue = 0
    items.map { it.itemType }.forEach {
        when (it) {
            is DiceModification -> {
                modifierValue += it.getModification()
            }
            is DiceAddition -> {
                additionValue += it.getAddition()
            }
        }
    }
    return Pair(modifierValue + modModifier, additionValue)
}