package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import de.bitb.spacerace.config.GAME_SPEED
import de.bitb.spacerace.config.MOVE_TIME
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData

class MovingImage : IMovingImage {

    private var flightTime = 1f

    private var targetPoint: Rectangle? = null
    private lateinit var doAfter: Array<out Action>

    override fun moveTo(movingImage: GameImage, imagePosition: PositionData, vararg doAfter: Action) {
        movingImage.movingState = MovingState.MOVING

        val moveTo = MoveToAction()
        val posX = imagePosition.posX //TODO muss das?
        val posY = imagePosition.posY
        moveTo.setPosition(posX, posY)
        moveTo.duration = getDurationToTarget(movingImage, imagePosition)

        this.targetPoint = null
        movingImage.addAction(moveTo, *doAfter)
    }

    override fun moveToPoint(movingImage: GameImage, targetImage: GameImage, vararg doAfter: Action) {
        val action = movingImage.getRunnableAction(Runnable {
            this.doAfter = doAfter
            movingImage.movingState = MovingState.MOVING
            movingImage.followImage = targetImage
            this.targetPoint = null
            flightTime = 1f
        })

        movingImage.addAction(action)
    }


    override fun moveToPoint(movingImage: GameImage, targetPoint: Rectangle, vararg doAfter: Action) {
        val action = movingImage.getRunnableAction(Runnable {
            this.doAfter = doAfter
            movingImage.movingState = MovingState.MOVING
            this.targetPoint = targetPoint
            flightTime = 1f
        })

        movingImage.addAction(action)
    }

    private fun getDistanceToTarget(movingImage: GameImage, targetPosition: PositionData): Float {
        val pos1 = Vector2(targetPosition.posX, targetPosition.posY)
        val pos2 = Vector2(movingImage.getCenterX(), movingImage.getCenterY())
        val target = pos1.sub(pos2)
        return Math.sqrt((target.x * target.x + target.y * target.y).toDouble()).toFloat()
    }

    private fun getDurationToTarget(movingImage: GameImage, targetPosition: PositionData): Float {
        return ((getDistanceToTarget(movingImage, targetPosition) / movingImage.movingSpeed) / GAME_SPEED.speed)
    }

    override fun actMovingTo(movingImage: GameImage, targetPosition: GameImage, deltaTime: Float) {
        if (targetPosition != GameImage.NONE) { //TODO mach das
            actMovingTo(movingImage, targetPosition.getBoundingRectangle(), deltaTime)
        }
    }

    override fun actMovingTo(movingImage: GameImage, targetPosition: Rectangle, deltaTime: Float) {
        when (movingImage.movingState) {
            MovingState.ROTATE_POINT -> {
            }
            MovingState.MOVING -> {
                movingAction(movingImage, targetPosition, deltaTime)
            }
            MovingState.NONE -> {
                movingImage.setCenterX(targetPosition.x)
                movingImage.setCenterY(targetPosition.y)

                movingImage.x = targetPosition.x - movingImage.width / 2
                movingImage.y = targetPosition.y - movingImage.height / 2
            }
        }
    }

    private fun movingAction(movingImage: GameImage, targetPosition: Rectangle, deltaTime: Float) {
        flightTime += deltaTime
        //get distance to target x & y
        var velocityX: Double = ((targetPosition.x - movingImage.getCenterX()).toDouble())
        var velocityY: Double = ((targetPosition.y - movingImage.getCenterY()).toDouble())
        val distance = Math.sqrt((velocityX * velocityX + velocityY * velocityY))

        //normalise
        if (distance != 0.0) {
            velocityX /= distance
            velocityY /= distance
        }

        movingImage.x += (velocityX * movingImage.movingSpeed * deltaTime * flightTime).toFloat()
        movingImage.y += (velocityY * movingImage.movingSpeed * deltaTime * flightTime).toFloat()

        val ninjaRectangle: Rectangle = movingImage.getBoundingRectangle()
        val shurikenRectangle: Rectangle = targetPosition
        if (ninjaRectangle.overlaps(shurikenRectangle)) { //this.targetPoint != null && !ninjaRectangle.overlaps(shurikenRectangle) ||
            movingImage.movingState = MovingState.NONE
            movingImage.followImage = GameImage.NONE
            movingImage.addAction(*doAfter)
        }

//        flightTime += deltaTime
//        //get distance to target x & y
//        var velocityX: Double = ((targetPosition.getCenterX() - movingImage.getCenterX()).toDouble())
//        var velocityY: Double = ((targetPosition.getCenterY() - movingImage.getCenterY()).toDouble())
//        val distance = Math.sqrt((velocityX * velocityX + velocityY * velocityY))
//
//        //normalise
//        if (distance != 0.0) {
//            velocityX /= distance
//            velocityY /= distance
//        }
//
//        movingImage.x += (velocityX * movingImage.movingSpeed * deltaTime * flightTime).toFloat()
//        movingImage.y += (velocityY * movingImage.movingSpeed * deltaTime * flightTime).toFloat()
//
//        val ninjaRectangle: Rectangle = movingImage.getBoundingRectangle()
//        val shurikenRectangle: Rectangle = targetPosition.getBoundingRectangle()
//        if (ninjaRectangle.overlaps(shurikenRectangle)) {
//            movingImage.movingState = MovingState.NONE
//            movingImage.followImage = GameImage.NONE
//            movingImage.addAction(*doAfter)
//        }
    }
}