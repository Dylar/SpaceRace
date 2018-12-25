package de.bitb.spacerace.model.enums

enum class ConnectionPoint {
    NONE, LEFT, RIGHT, UP, DOWN;

    fun getOpposite(): ConnectionPoint {
        return when (this) {
            ConnectionPoint.NONE -> NONE
            ConnectionPoint.LEFT -> RIGHT
            ConnectionPoint.RIGHT -> LEFT
            ConnectionPoint.UP -> DOWN
            ConnectionPoint.DOWN -> UP
        }
    }
}