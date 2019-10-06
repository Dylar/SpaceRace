package de.bitb.spacerace.database.items

import de.bitb.spacerace.database.player.PlayerData


fun PlayerData.getModifierValues(modModifier: Int = 0): Pair<Double, Int> {
    var modifierValue = 0.0
    var additionValue = 0
    storageItems.map { it.itemType }
            .forEach {
                when (it) {
                    is DiceModification -> {
                        modifierValue += it.diceModifier
                    }
                    is DiceAddition -> {
                        additionValue += it.diceAddition
                    }
                }
            }
    return Pair(modifierValue + modModifier, additionValue)
}