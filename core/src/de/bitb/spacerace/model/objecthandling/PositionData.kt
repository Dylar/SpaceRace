package de.bitb.spacerace.model.objecthandling

import de.bitb.spacerace.config.MOVING_SPEED

data class PositionData(var posX: Float = 0f,
                        var posY: Float = 0f,
                        var width: Float = 0f,
                        var height: Float = 0f) {

    var movingSpeed: Float = MOVING_SPEED

    fun setPosition(positionData: PositionData) {
        posX = positionData.posX
        posY = positionData.posY
    }

    fun isPosition(positionData: PositionData): Boolean {
        return positionData.posX == posX && positionData.posY == posY
    }

    fun getCenterPosX(positionData: PositionData): Float {
        return posX + width / 2 - positionData.width / 2
    }

    fun getCenterPosY(positionData: PositionData): Float {
        return posY + height / 2 - positionData.height / 2
    }

    fun withCenter(): PositionData {
        val posX = posX + width / 2
        val posY = posY + height / 2
        return copy(posX = posX, posY = posY)
    }
}