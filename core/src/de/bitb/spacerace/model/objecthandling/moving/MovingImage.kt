package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import de.bitb.spacerace.config.GAME_SPEED
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

class MovingImage : IMovingImage {

    override fun moveToPoint(movingObject: GameObject, positionData: PositionData) {
        val moveTo = MoveToAction()
        moveTo.setPosition(positionData.posX, positionData.posY)
        moveTo.duration = getDurationToTarget(movingObject, positionData)

        movingObject.getGameImage().addAction(moveTo)
    }

    override fun moveTo(movingObject: GameObject, targetPosition: PositionData, vararg doAfter: Action) {
        val gameImage = movingObject.getGameImage()
        gameImage.movingState = MovingState.MOVING

        val moveTo = MoveToAction()
        val posX = targetPosition.getCenterPosX(movingObject.positionData)
        val posY = targetPosition.getCenterPosY(movingObject.positionData)
        moveTo.setPosition(posX, posY)
        moveTo.duration = getDurationToTarget(movingObject, targetPosition)

        gameImage.addAction(moveTo, *doAfter)
        movingObject.positionData.setPosition(targetPosition)
    }

    fun moveTo(movingObject: GameObject, point: Double) {

//        val point = getRotationPoint(movingObject.getGameImage(), targetPosition)
//
//        val action = getRunnableAction(Runnable {
//            rotationPoint = targetPosition
//            setRotationPosition(movingObject.getGameImage(), point)
//            movingState = MovingState.ROTATE_POINT
//        })
//        val newPosition = targetPosition.copy(posX = point.x - targetPosition.width / 2, posY = point.y - targetPosition.height / 2)
//        moveTo(movingObject, newPosition, action)
    }

    override fun getDistanceToTarget(movingObject: GameObject, targetPosition: PositionData): Float {
        val vector = Vector2(targetPosition.posX + targetPosition.width / 2, targetPosition.posY + targetPosition.height / 2).sub(Vector2(movingObject.positionData.posX + movingObject.positionData.width / 2, movingObject.positionData.posY + movingObject.positionData.height / 2))
        return Math.sqrt((vector.x * vector.x + vector.y * vector.y).toDouble()).toFloat()
    }

    override fun getDurationToTarget(movingObject: GameObject, targetPosition: PositionData): Float {
        return ((getDistanceToTarget(movingObject, targetPosition) / movingObject.positionData.movingSpeed) / GAME_SPEED.speed)
    }
}