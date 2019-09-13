package de.bitb.spacerace.database.player

import de.bitb.spacerace.config.CREDITS_LOSE_AMOUNT
import de.bitb.spacerace.config.CREDITS_WIN_AMOUNT
import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.database.converter.IntListConverter
import de.bitb.spacerace.database.converter.PhaseConverter
import de.bitb.spacerace.database.converter.PlayerColorConverter
import de.bitb.spacerace.database.converter.PositionListConverter
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.NONE_POSITION
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import io.objectbox.BoxStore
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

val NONE_PLAYER_DATA: PlayerData = PlayerData()

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
        var credits: Int = START_CREDITS,
        var victories: Long = 0,

        @Convert(converter = PositionListConverter::class, dbType = String::class)
        var steps: ArrayList<PositionData> = ArrayList(),

        var controlToken: String = ""
) {

    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

    @JvmField
    var positionField: ToOne<FieldData> = ToOne(this, PlayerData_.positionField)

    @JvmField
    @Backlink(to = "owner")
    var mines: ToMany<FieldData> = ToMany(this, PlayerData_.mines)

    val gamePosition: PositionData
        get() = positionField.target.gamePosition

    val previousStep: PositionData
        get() = steps.let {
            if (it.size < 2) NONE_POSITION else it[it.size - 2]
        }

    fun setSteps(gamePosition: PositionData) {
        if (previousFieldSelected(gamePosition)) steps.let { it.removeAt(it.size - 1) }
        else steps.add(gamePosition)
    }

    private fun previousFieldSelected(gamePosition: PositionData): Boolean {
        return steps.size > 1 && previousStep.isPosition(gamePosition)
    }

    fun nextRound() {
        steps.clear()
        diceResults.clear()
        phase = Phase.END_ROUND
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

    fun stepsLeft(): Int =
            getMaxSteps() - (if (steps.isEmpty()) 0 else steps.size - 1)

    fun areStepsLeft(): Boolean =
            stepsLeft() > 0

    fun getMaxSteps(): Int = diceResults.sum().let { result -> if (diceResults.isNotEmpty() && result <= 0) 1 else result }

    fun isPreviousPosition(fieldPosition: PositionData) = steps.size > 1 && previousStep.isPosition(fieldPosition)

    fun canPlayerMoveTo(fieldData: FieldData): Boolean {
        val isMovePhase = phase == Phase.MOVE
        val isConnected = this isConnectedTo fieldData
        val isPreviousField = previousFieldSelected(fieldData.gamePosition)

        return isMovePhase && isConnected && (areStepsLeft() || isPreviousField)
    }

    infix fun isConnectedTo(fieldData: FieldData): Boolean =
            fieldData isConnectedTo positionField.target

//            playerController.getPlayerItems(playerColor).getModifierValues(1) //TODO do item shit
//                    .let { (mod, add) ->
//                        val diceResult = diceResults.sum()
//                        val result = (diceResult * mod + add).toInt()
//
//                        if (diceResults.isNotEmpty() && result <= 0) 1 else result
//                    }
}
