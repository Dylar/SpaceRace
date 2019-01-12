package de.bitb.spacerace.model.objecthandling

abstract class GameObject(var positionData: PositionData = PositionData())
    : DefaultFunction by object : DefaultFunction {} {

    open var id: Int = 0

    abstract fun getGameImage(): GameImage

    fun setBounds(x: Float, y: Float, width: Float, height: Float) {
        val image = getGameImage()
        image.setBounds(x, y, width, height)
        positionData.posX = image.x
        positionData.posY = image.y
        positionData.width = image.width
        positionData.height = image.height
    }

    fun setPosition(x: Float, y: Float) {
        val image = getGameImage()
        image.setPosition(x, y)
        positionData.posX = image.x
        positionData.posY = image.y
    }

    fun setPosition(positionData: PositionData) {
        setPosition(positionData.posX, positionData.posY)
//        setPosition(this.positionData.getCenterPosX(positionData), this.positionData.getCenterPosY(positionData))
    }

}