package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import de.bitb.spacerace.config.GAME_SPEED
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

class MovingImage : IMovingImage {

    override fun moveTo(movingObject: GameObject, targetPosition: PositionData) {
        val moveTo = MoveToAction()
        moveTo.setPosition(targetPosition.posX + movingObject.positionData.width / 2, targetPosition.posY + movingObject.positionData.height / 2)
        moveTo.duration = getDurationToTarget(movingObject, targetPosition)
        movingObject.getGameImage().addAction(moveTo)
        movingObject.positionData.setPosition(targetPosition)
    }

    override fun getDistanceToTarget(movingObject: GameObject, targetPosition: PositionData): Float {
        val vector = Vector2(targetPosition.posX + targetPosition.width / 2, targetPosition.posY + targetPosition.height / 2).sub(Vector2(movingObject.positionData.posX + movingObject.positionData.width / 2, movingObject.positionData.posY + movingObject.positionData.height / 2))
        return Math.sqrt((vector.x * vector.x + vector.y * vector.y).toDouble()).toFloat()
    }

    override fun getDurationToTarget(movingObject: GameObject, targetPosition: PositionData): Float {
        return ((getDistanceToTarget(movingObject, targetPosition) / movingObject.positionData.movingSpeed) / GAME_SPEED.speed)
    }
}