package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import de.bitb.spacerace.config.GAME_SPEED
import de.bitb.spacerace.model.objecthandling.GameImage

class MovingImage : IMovingImage {

    override fun moveTo(movingObject: GameImage, targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float) {
        val moveTo = MoveToAction()
        moveTo.setPosition(targetX - movingObject.positionData.width / 2, targetY - movingObject.positionData.height / 2)
        moveTo.duration = getDurationToTarget(movingObject, targetX, targetY, targetWidth, targetHeight)
        movingObject.addAction(moveTo)
    }

    override fun getDistanceToTarget(movingObject: GameImage, targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float): Float {
        val vector = Vector2(targetX + targetWidth / 2, targetY + targetHeight / 2).sub(Vector2(movingObject.positionData.posX + movingObject.positionData.width / 2, movingObject.positionData.posY + movingObject.positionData.height / 2))
        return Math.sqrt((vector.x * vector.x + vector.y * vector.y).toDouble()).toFloat()
    }

    override fun getDurationToTarget(movingObject: GameImage, targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float): Float {
        return ((getDistanceToTarget(movingObject, targetX, targetY, targetWidth, targetHeight) / movingObject.positionData.movingSpeed) / GAME_SPEED.speed)
    }
}