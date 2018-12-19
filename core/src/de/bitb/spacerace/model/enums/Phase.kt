package de.bitb.spacerace.model.enums

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.config.strings.GameStrings.PHASE_END_ROUND
import de.bitb.spacerace.config.strings.GameStrings.PHASE_MAIN1
import de.bitb.spacerace.config.strings.GameStrings.PHASE_MAIN2
import de.bitb.spacerace.config.strings.GameStrings.PHASE_MOVE

enum class Phase(val text: String, val color: Color) {
    MAIN1(PHASE_MAIN1, Color.MAROON),
    MOVE(PHASE_MOVE, Color.DARK_GRAY),
    MAIN2(PHASE_MAIN2, Color.FIREBRICK),
    END_ROUND(PHASE_END_ROUND, Color.PINK);

    companion object {
        fun next(phase: Phase): Phase {
            return when (phase) {
                Phase.MAIN1 -> MOVE
                Phase.MOVE -> MAIN2
                Phase.MAIN2 -> END_ROUND
                Phase.END_ROUND -> MAIN1
            }
        }

    }

    fun isMain(): Boolean {
        return this == MAIN1 || this == MAIN2
    }

    fun isMain1(): Boolean {
        return this == MAIN1
    }

    fun isMoving(): Boolean {
        return this == MOVE
    }

    fun isNextTurn(): Boolean {
        return this == END_ROUND
    }

}