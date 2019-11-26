package de.bitb.spacerace.grafik.model.enums

import com.badlogic.gdx.graphics.Color
import de.bitb.spacerace.config.strings.GameStrings.PHASE_END_ROUND
import de.bitb.spacerace.config.strings.GameStrings.PHASE_END_TURN
import de.bitb.spacerace.config.strings.GameStrings.PHASE_MAIN1
import de.bitb.spacerace.config.strings.GameStrings.PHASE_MAIN2
import de.bitb.spacerace.config.strings.GameStrings.PHASE_MOVE

enum class Phase(val text: String, val color: Color) {
    MAIN1(PHASE_MAIN1, Color.MAROON),
    MOVE(PHASE_MOVE, Color.DARK_GRAY),
    MAIN2(PHASE_MAIN2, Color.FIREBRICK),
    END_TURN(PHASE_END_TURN, Color.PINK),
    END_ROUND(PHASE_END_ROUND, Color.BROWN);

    companion object {
        fun next(phase: Phase): Phase {
            return when (phase) {
                MAIN1 -> MOVE
                MOVE -> MAIN2
                MAIN2 -> END_TURN
                END_TURN -> END_TURN
                END_ROUND -> MAIN1
            }
        }
    }

    fun isMain() = this == MAIN1 || this == MAIN2
    fun isMain1() = this == MAIN1
    fun isMoving() = this == MOVE
    fun isMain2() = this == MAIN2
    fun isEndTurn() = this == END_TURN
    fun isEndRound(): Boolean = this == END_ROUND

}