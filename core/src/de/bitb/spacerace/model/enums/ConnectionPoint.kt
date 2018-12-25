package de.bitb.spacerace.model.enums

enum class ConnectionPoint {
    NONE, LEFT, RIGHT, TOP, BOTTOM;

    fun getOpposite(): ConnectionPoint {
        return when (this) {
            ConnectionPoint.NONE -> NONE
            ConnectionPoint.LEFT -> RIGHT
            ConnectionPoint.RIGHT -> LEFT
            ConnectionPoint.TOP -> BOTTOM
            ConnectionPoint.BOTTOM -> TOP
        }
    }
}