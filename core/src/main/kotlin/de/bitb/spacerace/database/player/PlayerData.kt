package de.bitb.spacerace.database.player

import de.bitb.spacerace.config.CREDITS_LOSE_AMOUNT
import de.bitb.spacerace.config.CREDITS_WIN_AMOUNT
import de.bitb.spacerace.config.START_CREDITS
import de.bitb.spacerace.database.converter.IntListConverter
import de.bitb.spacerace.database.converter.PhaseConverter
import de.bitb.spacerace.database.converter.PlayerColorConverter
import de.bitb.spacerace.database.converter.PositionListConverter
import de.bitb.spacerace.database.items.ItemData
import de.bitb.spacerace.database.items.MultiDice
import de.bitb.spacerace.database.items.getModifierValues
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.isConnectedTo
import de.bitb.spacerace.grafik.model.enums.Phase
import de.bitb.spacerace.grafik.model.items.ItemType
import de.bitb.spacerace.grafik.model.objecthandling.NONE_POSITION
import de.bitb.spacerace.grafik.model.objecthandling.PositionData
import de.bitb.spacerace.grafik.model.player.PlayerColor
import io.objectbox.BoxStore
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import kotlin.math.roundToInt

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

    @JvmField
    var storageItems: ToMany<ItemData> = ToMany(this, PlayerData_.storageItems)

    @JvmField
    var attachedItems: ToMany<ItemData> = ToMany(this, PlayerData_.attachedItems)

    @JvmField
    var equippedItems: ToMany<ItemData> = ToMany(this, PlayerData_.equippedItems)

    @JvmField
    var activeItems: ToMany<ItemData> = ToMany(this, PlayerData_.equippedItems)

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

    fun addRandomWin(): Int {
        val win = (Math.random() * CREDITS_WIN_AMOUNT).toInt() + 1
        credits += win + 1
        return win
    }

    fun substractRandomWin(): Int {
        val lose = (Math.random() * CREDITS_LOSE_AMOUNT).toInt() + 1
        credits -= lose
        return lose
    }

    fun stepsLeft(): Int {
        val steps = when {
            steps.isEmpty() -> 0
            else -> steps.size - 1
        }
        return getMaxSteps() - steps
    }

    fun areStepsLeft(): Boolean =
            stepsLeft() > 0

    fun getMaxSteps(): Int {
        val (multiValue, addValue) = getModifierValues()
        val result: Int = (diceResults.sum() * (multiValue + 1) + addValue).roundToInt()
        return when {
            result <= 0 && diceResults.isEmpty() -> 0
            result <= 0 && diceResults.isNotEmpty() -> 1
            else -> result
        }
    }

    fun isPreviousPosition(fieldPosition: PositionData) = steps.size > 1 && previousStep.isPosition(fieldPosition)

    fun canPlayerMoveTo(fieldData: FieldData): Boolean {
        val isMovePhase = phase == Phase.MOVE
        val isConnected = this isConnectedTo fieldData
        val isPreviousField = previousFieldSelected(fieldData.gamePosition)
//TODO path finding
        return isMovePhase && isConnected && (areStepsLeft() || isPreviousField)
    }

    fun clearTurn() {
        steps.clear()
        diceResults.clear()
    }

    fun hasDicedEnough(): Boolean = diceResults.size == maxDice()

    fun maxDice(): Int {
        val items: List<MultiDice> = activeItems
                .map { it.itemInfo }
                .filterIsInstance<MultiDice>()
        return 1 + items.sumBy { it.diceAmount }
    }

    fun sellableItems(itemType: ItemType): Int =
            storageItems.count { it.itemInfo.type == itemType } +
                    equippedItems.count { it.itemInfo.type == itemType }
}

infix fun PlayerData.isConnectedTo(fieldData: FieldData): Boolean =
        fieldData isConnectedTo positionField.target