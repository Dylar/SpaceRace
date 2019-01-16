package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import de.bitb.spacerace.config.GAME_SPEED
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

class MovingImage : IMovingImage {

//    override fun moveToPoint(movingObject: GameObject, gamePosition: PositionData, vararg doAfter: Action) {
//        val gameImage = movingObject.getGameImage()
//        gameImage.movingState = MovingState.MOVING
//
//        val moveTo = MoveToAction()
//        moveTo.setPosition(gamePosition.getCenterPosX(), gamePosition.getCenterPosY())
//        moveTo.duration = getDurationToTarget(movingObject, gamePosition)
//
//        gameImage.addAction(moveTo, *doAfter)
//    }

    override fun moveTo(movingObject: GameObject, imagePosition: PositionData, gamePosition: PositionData, vararg doAfter: Action) {
        val gameImage = movingObject.getGameImage()
        gameImage.movingState = MovingState.MOVING

        val moveTo = MoveToAction()
        val posX = imagePosition.posX
        val posY = imagePosition.posY
        moveTo.setPosition(posX, posY)
        moveTo.duration = getDurationToTarget(movingObject, imagePosition)

        gameImage.addAction(moveTo, *doAfter)
        movingObject.gamePosition.setPosition(gamePosition)
    }

    override fun getDistanceToTarget(movingObject: GameObject, targetPosition: PositionData): Float {
        val pos1 = Vector2(targetPosition.posX, targetPosition.posY)
        val pos2 = Vector2(movingObject.getGameImage().getCenterX(), movingObject.getGameImage().getCenterY())
        val target = pos1.sub(pos2)
        return Math.sqrt((target.x * target.x + target.y * target.y).toDouble()).toFloat()
//        return distance(pos1, pos2).toFloat()
    }

    fun distance(object1: Vector2, object2: Vector2): Double {
        return Math.sqrt(Math.pow((object2.x - object1.x).toDouble(), 2.0) + Math.pow((object2.y - object1.y).toDouble(), 2.0)) / 2
    }

    override fun getDurationToTarget(movingObject: GameObject, targetPosition: PositionData): Float {
        return ((getDistanceToTarget(movingObject, targetPosition) / movingObject.gamePosition.movingSpeed) / GAME_SPEED.speed)
    }
}