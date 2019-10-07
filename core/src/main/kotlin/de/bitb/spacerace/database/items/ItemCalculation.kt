package de.bitb.spacerace.database.items

import de.bitb.spacerace.database.player.PlayerData
import io.objectbox.relation.ToMany


fun PlayerData.getModifierValues(modModifier: Int = 0): Pair<Double, Int> {
    var modifierValue = 0.0
    var additionValue = 0
    fun addValues(storageItems: List<ItemData>) {
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
    }
    addValues(equippedItems)
    addValues(attachedItems)

    return Pair(modifierValue + modModifier, additionValue)
}