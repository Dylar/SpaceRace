package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Color

enum class PlayerColor(val color: Color) {
    RED(Color.RED),
    GREEN(Color.GREEN),
    YELLOW(Color.YELLOW),
    BLUE(Color.BLUE),
    PINK(Color.PINK),
    TEAL(Color.TEAL),
    CORAL(Color.CORAL),
    SCARLET(Color.SCARLET),
    CHARTREUSE(Color.CHARTREUSE),
    BLACK(Color.BLACK),
    SLATE(Color.SLATE),
    NAVY(Color.NAVY),

    NONE(Color.BLACK);

    fun next(): PlayerColor{
        return when (this) {
            RED -> GREEN
            GREEN -> YELLOW
            YELLOW -> BLUE
            BLUE -> PINK
            PINK -> TEAL
            TEAL -> CORAL
            CORAL -> SCARLET
            SCARLET -> CHARTREUSE
            CHARTREUSE -> BLACK
            BLACK -> SLATE
            SLATE -> NAVY
            NAVY -> RED
            NONE -> NONE
        }
    }
}