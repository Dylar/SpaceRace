package de.bitb.spacerace.model.enums

import com.badlogic.gdx.graphics.Color

enum class Phase(val color: Color) {
    MAIN1(Color.MAROON), MOVE(Color.DARK_GRAY), MAIN2(Color.FIREBRICK), NEXT_TURN(Color.PINK);

    companion object {
        fun next(phase: Phase): Phase {
            return when (phase) {
                Phase.MAIN1 -> MOVE
                Phase.MOVE -> MAIN2
                Phase.MAIN2 -> NEXT_TURN
                Phase.NEXT_TURN -> MAIN1
            }
        }

    }

    fun isMain(): Boolean {
        return this == MAIN1 || this == MAIN2
    }

    fun isNextTurn():Boolean{
        return this == NEXT_TURN
    }
}