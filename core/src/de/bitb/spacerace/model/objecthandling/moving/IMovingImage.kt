package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.scenes.scene2d.Action
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

interface IMovingImage {

    fun moveTo(movingObject: GameObject, imagePosition: PositionData, gamePosition: PositionData = imagePosition, vararg doAfter: Action)
    fun getDistanceToTarget(movingObject: GameObject, targetPosition: PositionData): Float
    fun getDurationToTarget(movingObject: GameObject, targetPosition: PositionData): Float

    fun actMoving(movingImage: GameImage, targetPosition: GameImage, deltaTime: Float)
//    fun moveToPoint(movingObject: GameObject, imagePosition: PositionData, gamePosition: PositionData, vararg doAfter: Action)
    fun moveToPoint(movingObject: GameObject, targetImage: GameImage, gamePosition: PositionData, vararg doAfter: Action)
}