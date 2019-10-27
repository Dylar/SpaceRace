package de.bitb.spacerace.database.items

interface Ship{
    var speed: Float
}

interface EquipItem {
    var equipped: Boolean
}

interface ActivatableItem
interface AttachableItem
interface DisposableItem




interface RemoveEffect
interface DiceAddition {
    val diceAddition: Int
}

interface DiceModification {
    val diceModifier: Double
}

interface MultiDice {
    val diceAmount: Int
}