package de.bitb.spacerace.database.items


interface EquipItem
interface ActivableItem
interface AttachableItem


interface DiceAddition {
    val diceAddition: Int
}

interface DiceModification {
    val diceModifier: Double
}

interface MultiDice {
    val diceAmount: Int
}