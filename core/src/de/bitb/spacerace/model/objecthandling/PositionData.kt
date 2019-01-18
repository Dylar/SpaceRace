package de.bitb.spacerace.model.objecthandling

data class PositionData(var posX: Float = 0f,
                        var posY: Float = 0f,
                        var width: Float = 0f,
                        var height: Float = 0f) {

    fun setPosition(positionData: PositionData) {
        posX = positionData.posX
        posY = positionData.posY
    }

    fun isPosition(positionData: PositionData): Boolean {
        return positionData.posX == posX && positionData.posY == posY
    }

}