package de.bitb.spacerace.model.player

import de.bitb.spacerace.config.CREDITS_LOSE_AMOUNT
import de.bitb.spacerace.config.CREDITS_WIN_AMOUNT
import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.database.converter.IntListConverter
import de.bitb.spacerace.database.converter.PhaseConverter
import de.bitb.spacerace.database.converter.PlayerColorConverter
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.space.fields.SpaceField
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class PlayerData(
        @Id
        var uuid: Long = 0,
        @Convert(converter = PlayerColorConverter::class, dbType = String::class)
        var playerColor: PlayerColor = PlayerColor.NONE,
        @Convert(converter = IntListConverter::class, dbType = String::class)
        var diceResults: MutableList<Int> = ArrayList(),
        @Convert(converter = PhaseConverter::class, dbType = String::class)
        var phase: Phase = Phase.MAIN1,
        var steps: ToMany<PositionData>? = null,
        var credits: Int = START_CREDITS,
        var victories: Long = 0,
        var controlToken: String = "") {

    @Transient
    val playerItems: PlayerItems = PlayerItems(playerColor)

    val previousStep: PositionData
        get() = steps?.let {
            if (it.size < 2) PositionData() else it[it.size - 2]!!
        } ?: PositionData()

    fun setSteps(playerData: PlayerData, spaceField: SpaceField) {
        val sameField = previousFieldSelected(playerData, spaceField)
        if (sameField) {
            steps?.let {
                playerData.steps?.let {
                    it.removeAt(it.size - 1)
                }
            }
        } else {
            playerData.steps!!.add(spaceField.gamePosition)
        }
    }

    private fun previousFieldSelected(playerData: PlayerData, spaceField: SpaceField): Boolean {
        return playerData.steps!!.size > 1 && playerData.previousStep.isPosition(spaceField.gamePosition)
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
        return getMaxSteps() - (if (steps!!.isEmpty()) 0 else steps!!.size - 1)
    }

    fun areStepsLeft(): Boolean {
        return stepsLeft() > 0
    }


    fun canMove(): Boolean {
        return phase.isMoving() && areStepsLeft()
    }

    fun nextRound() {
        steps!!.clear()
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
