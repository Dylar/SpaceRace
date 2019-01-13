package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.scenes.scene2d.Action
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

interface IMovingImage {

    fun moveToPoint(movingObject: GameObject, positionData: PositionData)
    fun moveTo(movingObject: GameObject, targetPosition: PositionData, vararg doAfter: Action)
    fun getDistanceToTarget(movingObject: GameObject, targetPosition: PositionData): Float
    fun getDurationToTarget(movingObject: GameObject, targetPosition: PositionData): Float

}