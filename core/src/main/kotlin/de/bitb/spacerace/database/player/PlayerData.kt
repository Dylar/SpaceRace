package de.bitb.spacerace.database.player

import de.bitb.spacerace.config.CREDITS_LOSE_AMOUNT
import de.bitb.spacerace.config.CREDITS_WIN_AMOUNT
import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.database.converter.IntListConverter
import de.bitb.spacerace.database.converter.PhaseConverter
import de.bitb.spacerace.database.converter.PlayerColorConverter
import de.bitb.spacerace.database.converter.PositionDataConverter
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.model.enums.Phase
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.model.space.fields.SpaceField
import io.objectbox.BoxStore
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
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

        @Convert(converter = PositionDataConverter::class, dbType = String::class)
        var steps: ArrayList<PositionData> = ArrayList(),

        var controlToken: String = ""
) {

//    @Transient
//    @JvmField
//    protected var __boxStore: BoxStore? = null
//
//    @JvmField
//    var positionField: ToOne<FieldData> = ToOne(this, PlayerData_.positionField)

    val previousStep: PositionData
        get() = steps.let {
            if (it.size < 2) PositionData() else it[it.size - 2]
        }

    fun setSteps(playerData: PlayerData, spaceField: SpaceField) {
        val sameField = previousFieldSelected(playerData, spaceField)
        if (sameField) {
            steps.let {
                playerData.steps.let {
                    it.removeAt(it.size - 1)
                }
            }
        } else {
            playerData.steps.add(spaceField.gamePosition)
        }
    }

    private fun previousFieldSelected(playerData: PlayerData, spaceField: SpaceField): Boolean {
        return playerData.steps.size > 1 && playerData.previousStep.isPosition(spaceField.gamePosition)
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

    fun nextPhase() {
        phase = Phase.next(phase)
    }

}
