package de.bitb.spacerace.grafik.model.objecthandling

import io.objectbox.annotation.Id

val NONE_POSITION = PositionData()

//@Entity
data class PositionData(
        var posX: Float = 0f,
        var posY: Float = 0f,
        var width: Float = 0f,
        var height: Float = 0f,
        @Id
        var uuid: Long = 0) {

    fun setPosition(positionData: PositionData) {
        posX = positionData.posX
        posY = positionData.posY
    }

    fun isPosition(positionData: PositionData): Boolean {
        return positionData.posX == posX && positionData.posY == posY
    }

    fun getCenterX() = posX + width / 2

    fun getCenterY() = posY + height / 2
}