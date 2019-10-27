package de.bitb.spacerace.grafik.model.objecthandling

abstract class GameObject(
        var gamePosition: PositionData = PositionData()
) {

    open var id: Int = 0

    abstract fun getGameImage(): GameImage

    fun setBounds(x: Float, y: Float, width: Float, height: Float) {
        val image = getGameImage()
        image.setBounds(x, y, width, height)
        gamePosition.posX = x
        gamePosition.posY = y
        gamePosition.width = width
        gamePosition.height = height
    }

    fun setPosition(x: Float, y: Float) {
        val image = getGameImage()
        image.setPosition(x, y)
        gamePosition.posX = x
        gamePosition.posY = y
    }

    fun centerImage() {
        val image = getGameImage()
        image.x = gamePosition.posX + image.width / 2
        image.y = gamePosition.posY + image.height / 2
    }

    fun setPosition(positionData: PositionData) {
        setPosition(positionData.posX, positionData.posY)
        centerImage()
    }

}