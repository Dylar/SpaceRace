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

    fun getCenterPosX(): Float {
        return posX + width / 2
    }

    fun getCenterPosY(): Float {
        return posY + height / 2 //TODO why a quarter?
    }

    fun centerPosition(width: Float, height: Float) {
        posX += width / 2
        posY += height / 2
    }

}