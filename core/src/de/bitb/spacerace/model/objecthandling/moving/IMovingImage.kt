package de.bitb.spacerace.model.objecthandling.moving

import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

interface IMovingImage {

    fun moveTo(movingObject: GameObject, targetPosition: PositionData)
    fun getDistanceToTarget(movingObject: GameObject, targetPosition: PositionData): Float
    fun getDurationToTarget(movingObject: GameObject, targetPosition: PositionData): Float

}