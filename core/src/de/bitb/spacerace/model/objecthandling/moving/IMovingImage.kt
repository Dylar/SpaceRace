package de.bitb.spacerace.model.objecthandling.moving

import de.bitb.spacerace.model.objecthandling.GameImage

interface IMovingImage {

    fun moveTo(movingObject: GameImage, targetX: Float = movingObject.positionData.posX, targetY: Float = movingObject.positionData.posY, targetWidth: Float = 0f, targetHeight: Float = 0f)
    fun getDistanceToTarget(movingObject: GameImage, targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float): Float
    fun getDurationToTarget(movingObject: GameImage, targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float): Float

}