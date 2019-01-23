package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import de.bitb.spacerace.config.GAME_SPEED
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData

class MovingImage : IMovingImage {

    private var flightTime = 1f

    private var doAfter: MutableList<Action> = ArrayList()

    override fun moveTo(movingImage: GameImage, imagePosition: PositionData, vararg doAfter: Action) {
        movingImage.movingState = MovingState.MOVING

        val moveTo = MoveToAction()
        val posX = imagePosition.posX //TODO muss das?
        val posY = imagePosition.posY
        moveTo.setPosition(posX, posY)
        moveTo.duration = getDurationToTarget(movingImage, imagePosition)

        movingImage.targetPoint = null
        movingImage.addAction(moveTo, *doAfter)
    }

    override fun moveToPoint(movingImage: GameImage, targetImage: GameImage, vararg doAfter: Action) {
        val action = movingImage.getRunnableAction(Runnable {
            this.doAfter.addAll(doAfter)
            movingImage.movingState = MovingState.MOVING
            movingImage.followImage = targetImage
            movingImage.targetPoint = null
            flightTime = 1f
        })

        movingImage.addAction(action)
    }

    override fun moveToPoint(movingImage: GameImage, targetPoint: Rectangle, vararg doAfter: Action) {
        val action = movingImage.getRunnableAction(Runnable {
            this.doAfter.addAll(doAfter)
            movingImage.movingState = MovingState.MOVING
            movingImage.targetPoint = targetPoint
            flightTime = 1f
        })

        movingImage.addAction(action)
    }

    override fun patrollingBetweenPoints(movingImage: GameImage, targetPoint1: Rectangle, targetPoint2: Rectangle, vararg doAfter: Action) {
        val action = movingImage.getRunnableAction(Runnable {
            this.doAfter.addAll(doAfter)
            this.doAfter.add(getPatrollingAction(movingImage, targetPoint1, targetPoint2))
            movingImage.movingState = MovingState.MOVING
            movingImage.targetPoint = targetPoint2
            flightTime = 1f
        })

        movingImage.addAction(action)
    }

    private fun getPatrollingAction(image: GameImage, targetPosition1: Rectangle, targetPosition2: Rectangle): RunnableAction {
        val action = image.getRunnableAction(Runnable {
            (image as IMovingImage).patrollingBetweenPoints(image, targetPosition2, targetPosition1)
        })
        return action
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

    override fun actMovingTo(delta: Float, movingImage: GameImage, targetPosition1: GameImage) {
        if (targetPosition1 != GameImage.NONE) {
            actMovingTo(delta, movingImage, targetPosition1.boundingRectangle)
        }
    }

    override fun actMovingTo(delta: Float, movingImage: GameImage, targetPosition1: Rectangle) {
        when (movingImage.movingState) {
            MovingState.ROTATE_POINT -> {
            }
            MovingState.MOVING -> {
                movingAction(delta, movingImage, targetPosition1)
            }
            MovingState.NONE -> {
                movingImage.setCenterX(targetPosition1.x)
                movingImage.setCenterY(targetPosition1.y)

                movingImage.x = targetPosition1.x - movingImage.width / 2
                movingImage.y = targetPosition1.y - movingImage.height / 2
            }
        }
    }

    private fun movingAction(deltaTime: Float, movingImage: GameImage, targetPosition: Rectangle) {
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

        val ninjaRectangle: Rectangle = movingImage.boundingRectangle
        val shurikenRectangle: Rectangle = targetPosition
        if (ninjaRectangle.overlaps(shurikenRectangle)) {
            flightTime = 4f
            movingImage.movingState = MovingState.NONE
            movingImage.followImage = GameImage.NONE
            movingImage.targetPoint = null
            movingImage.addAction(*doAfter.toTypedArray())
            doAfter.clear()
        }

    }
}