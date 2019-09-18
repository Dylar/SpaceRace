package de.bitb.spacerace.model.player

import com.badlogic.gdx.graphics.Color

enum class PlayerColor(val color: Color) {
    RED(Color.RED),
    GREEN(Color.GREEN),
    YELLOW(Color.YELLOW),
    PINK(Color.PINK),
    TEAL(Color.TEAL),
    ORANGE(Color.ORANGE),
    SLATE(Color.SLATE),
    NAVY(Color.NAVY),
    WHITE(Color.WHITE),

    NONE(Color.CLEAR);

    fun next(): PlayerColor {
        return when (this) {
            RED -> GREEN
            GREEN -> YELLOW
            YELLOW -> PINK
            PINK -> TEAL
            TEAL -> ORANGE
            ORANGE -> SLATE
            SLATE -> NAVY
            NAVY -> NAVY
            WHITE -> RED

            NONE -> NONE
        }
    }

    companion object {
        fun get(color: String): PlayerColor {
            return values().firstOrNull { it.toString() == color } ?: NONE
        }
    }

}

fun Array<out PlayerColor>.toName(): Array<String> = this.map { it.name }.toTypedArray()