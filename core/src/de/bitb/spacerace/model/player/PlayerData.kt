package de.bitb.spacerace.model.player

import de.bitb.spacerace.Logger
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.space.fields.SpaceField

data class PlayerData(val playerColor: PlayerColor = PlayerColor.NONE) {

    val playerItems = PlayerItems(playerColor)

    var credits = 0
    var diceResult: Int = 0

    var phase: Phase = Phase.MAIN1

    var fieldPosition: SpaceField = SpaceField.NONE

    var steps: MutableList<SpaceField> = ArrayList()
    var previousStep: SpaceField = SpaceField.NONE
        get() = if (steps.size < 2) SpaceField.NONE else steps[steps.size - 2]

    fun dice(maxResult: Int = 6) {
        diceResult += (Math.random() * maxResult).toInt() + 1
        Logger.println("DiceResult: $diceResult")
    }

    fun getMaxSteps(): Int {
        var mod = 1f
        for (diceModItem in playerItems.diceModItems) {
            mod += diceModItem.getModification()
        }
        var add = 0
        for (diceAddItem in playerItems.diceAddItems) {
            add += diceAddItem.getAddition()
        }
        val result = (diceResult * mod + add).toInt()
        return if (diceResult != 0 && result == 0) 1 else result
    }

    fun stepsLeft(): Int {
        return getMaxSteps() - (if (steps.isEmpty()) 0 else steps.size - 1)
    }

    fun areStepsLeft(): Boolean {
        return stepsLeft() > 0
    }

    fun canMove(): Boolean {
        return phase.isMoving() && areStepsLeft()
    }

    fun nextRound() {
        steps = ArrayList()
        diceResult = 0
        phase = Phase.MAIN1
    }

    fun addRandomWin(): Int {
        val win = (Math.random() * 1000).toInt() + 1
        credits += win
        return win
    }

    fun substractRandomWin(): Int {
        val lose = (Math.random() * 500).toInt() + 1
        credits -= lose
        return lose
    }

}