package de.bitb.spacerace.model.objecthandling.moving

import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData

interface IMovingImage {

    fun moveTo(movingObject: GameImage, positionData: PositionData)
    fun getDistanceToTarget(movingObject: GameImage, positionData: PositionData): Float
    fun getDurationToTarget(movingObject: GameImage, positionData: PositionData): Float

}