package de.bitb.spacerace.model.enums

enum class ConnectionPoint {
    NONE, LEFT, RIGHT, TOP, BOTTOM;

    fun getOpposite(): ConnectionPoint {
        return when (this) {
            NONE -> NONE
            LEFT -> RIGHT
            RIGHT -> LEFT
            TOP -> BOTTOM
            BOTTOM -> TOP
        }
    }
}