package de.bitb.spacerace.model.player

import de.bitb.spacerace.Logger
import de.bitb.spacerace.config.CREDITS_LOSE_AMOUNT
import de.bitb.spacerace.config.CREDITS_WIN_AMOUNT
import de.bitb.spacerace.config.DICE_MAX
import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.SpaceField

data class PlayerData(val playerColor: PlayerColor = PlayerColor.NONE) {

    val playerItems = PlayerItems(playerColor)

    var credits = START_CREDITS
    var diceResults: MutableList<Int> = ArrayList()

    var phase: Phase = Phase.MAIN1

    var steps: MutableList<PositionData> = ArrayList()
    var previousStep: PositionData = PositionData()
        get() = if (steps.size < 2) PositionData() else steps[steps.size - 2]

    fun canDice(): Boolean {
        if (!phase.isMain1()) {
            return false
        }

        var diceCharges = 1
        for (diceModItem in playerItems.multiDiceItem) {
            diceCharges += diceModItem.getAmount()
        }
        return diceResults.size < diceCharges
    }

    fun dice(maxResult: Int = DICE_MAX) {
        diceResults.add((Math.random() * maxResult).toInt() + 1)
        Logger.println("DiceResult: $diceResults")
    }

    fun setSteps(playerData: PlayerData, spaceField: SpaceField) {
        val sameField = previousFieldSelected(playerData, spaceField)
        if (sameField) {
            playerData.steps.removeAt(playerData.steps.size - 1)
        } else {
            playerData.steps.add(spaceField.gamePosition)
        }
    }

    private fun previousFieldSelected(playerData: PlayerData, spaceField: SpaceField): Boolean {
        return playerData.steps.size > 1 && playerData.previousStep.isPosition(spaceField.gamePosition)
    }

    fun getMaxSteps(): Int {
        var mod = 1f
        playerItems.diceModItems.forEach {
            mod += it.getModification()
        }
        var add = 0
        playerItems.diceAddItems.forEach {
            add += it.getAddition()
        }
        var diceResult = 0
        diceResults.forEach { diceResult += it }

        val result = (diceResult * mod + add).toInt()
        return if (!diceResults.isEmpty() && result == 0) 1 else result
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
        diceResults.clear()
        phase = Phase.MAIN1
    }

    fun addRandomWin(): Int {
        val win = (Math.random() * CREDITS_WIN_AMOUNT).toInt() + 1
        credits += win
        return win
    }

    fun substractRandomWin(): Int {
        val lose = (Math.random() * CREDITS_LOSE_AMOUNT).toInt() + 1
        credits -= lose
        return lose
    }

}