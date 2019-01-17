package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import de.bitb.spacerace.config.GAME_SPEED
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.FIELD_BORDER
import de.bitb.spacerace.config.dimensions.Dimensions.GameDimensions.PLAYER_BORDER
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.rotating.IRotatingImage
import de.bitb.spacerace.model.objecthandling.rotating.RotatingImage

class MovingImage : IMovingImage {

    private var arrived: Boolean = true
    private var remainingFlightTime = 3.0
    private val speedPerSecond = FIELD_BORDER * 2

    private lateinit var doAfter: Array<out Action>

    override fun moveTo(movingObject: GameObject, imagePosition: PositionData, vararg doAfter: Action) {
        val gameImage = movingObject.getGameImage()
        gameImage.movingState = MovingState.MOVING

        val moveTo = MoveToAction()
        val posX = imagePosition.posX
        val posY = imagePosition.posY
        moveTo.setPosition(posX, posY)
        moveTo.duration = getDurationToTarget(movingObject, imagePosition)

        gameImage.addAction(moveTo, *doAfter)
    }

    override fun moveToPoint(movingObject: GameObject, targetImage: GameImage, vararg doAfter: Action) {
        val action = movingObject.getGameImage().getRunnableAction(Runnable {
            this.doAfter = doAfter
            val movingImage = movingObject.getGameImage()
            movingImage.movingState = MovingState.MOVING
            movingImage.followImage = targetImage
            arrived = false
            remainingFlightTime = 4.0
        })

        movingObject.getGameImage().addAction(action)

    }

    private fun getDistanceToTarget(movingObject: GameObject, targetPosition: PositionData): Float {
        val pos1 = Vector2(targetPosition.posX, targetPosition.posY)
        val pos2 = Vector2(movingObject.getGameImage().getCenterX(), movingObject.getGameImage().getCenterY())
        val target = pos1.sub(pos2)
        return Math.sqrt((target.x * target.x + target.y * target.y).toDouble()).toFloat()
    }

    private fun getDurationToTarget(movingObject: GameObject, targetPosition: PositionData): Float {
        return ((getDistanceToTarget(movingObject, targetPosition) / movingObject.gamePosition.movingSpeed) / GAME_SPEED.speed)
    }

    override fun actMoving(movingImage: GameImage, targetPosition: GameImage, deltaTime: Float) {
        when (movingImage.movingState) {
            MovingState.MOVING -> {
                if (movingImage.followImage != GameImage.NONE) {
                    remainingFlightTime -= deltaTime
                    if (remainingFlightTime <= 0) {
                        remainingFlightTime = 2.0
                    }

                    //get distance to target x & y
                    var velocityX = (targetPosition.getCenterX() - movingImage.getCenterX()) / remainingFlightTime
                    var velocityY = (targetPosition.getCenterY() - movingImage.getCenterY()) / remainingFlightTime
                    val distance = Math.sqrt((velocityX * velocityX + velocityY * velocityY))

                    //normalise
                    if (distance != 0.0) {
                        velocityX /= distance
                        velocityY /= distance
                    }

                    if (!arrived) {
                        movingImage.x += (velocityX * speedPerSecond * deltaTime).toFloat()
                        movingImage.y += (velocityY * speedPerSecond * deltaTime).toFloat()

                        val ninjaRectangle: Rectangle = movingImage.getBoundingRectangle()
                        val shurikenRectangle: Rectangle = targetPosition.getBoundingRectangle()
                        if (ninjaRectangle.overlaps(shurikenRectangle)) {
                            arrived = true

                            movingImage.movingState = MovingState.NONE
                            movingImage.followImage = GameImage.NONE
                            movingImage.addAction(*doAfter)
                        }
                    }
                }
            }
            MovingState.ROTATE_POINT -> {
            }
            MovingState.NONE -> {
                if (targetPosition != GameImage.NONE) {
                    movingImage.setCenterX(targetPosition.getCenterX())
                    movingImage.setCenterY(targetPosition.getCenterY())

                    movingImage.x = movingImage.followImage.getCenterX() - movingImage.width / 2
                    movingImage.y = movingImage.followImage.getCenterY() - movingImage.height / 2
                }
            }
        }

    }
}