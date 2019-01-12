package de.bitb.spacerace.model.objecthandling.moving

import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

interface IMovingImage {

    fun moveTo(movingObject: GameObject, positionData: PositionData)
    fun getDistanceToTarget(movingObject: GameObject, positionData: PositionData): Float
    fun getDurationToTarget(movingObject: GameObject, positionData: PositionData): Float

}