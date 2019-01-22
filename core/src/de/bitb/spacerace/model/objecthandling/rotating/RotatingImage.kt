package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import de.bitb.spacerace.config.dimensions.Dimensions.NINETY_DEGREE
import de.bitb.spacerace.config.dimensions.Dimensions.ONE_EIGHTY_DEGREE
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameImage.Companion.NONE
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.utils.CalculationUtils


class RotatingImage : IRotatingImage {

    private val slice: Float = (2 * Math.PI / 360).toFloat()
    private var angle = 0.0
    private var radius: Double = 0.0
    private var currentRotation: Float = 0f

    override fun setRotationRadius(radius: Double) {
        this.radius = radius
    }

    override fun getRotationAction(gameImage: GameImage, followImage: GameImage): RunnableAction {
        return gameImage.getRunnableAction(Runnable {
            gameImage.followImage = followImage
            gameImage.movingState = MovingState.ROTATE_POINT
        })
    }

    private fun getRotationPoint(posX: Float, posY: Float, angle: Double): Vector3 {
        return CalculationUtils.calculateRotationPoint(
                Vector2(posX, posY),
                radius,
                angle
        )
    }

    override fun getRotationPosition(gameImage: GameImage, targetPosition: GameImage): PositionData {
        val point = getRotationPoint(gameImage, targetPosition, angle)
        return PositionData(posX = point.x, posY = point.y)
    }

    private fun getRotationPoint(gameImage: GameImage, followImage: GameImage, angle: Double): Vector3 {
        val posX = followImage.getCenterX() - gameImage.width / 2
        val posY = followImage.getCenterY() - gameImage.height / 2
        return getRotationPoint(posX, posY, angle)
    }

    private fun setRotationPosition(gameImage: GameImage, point: Vector3) {
        gameImage.setPosition(point.x - point.z / 2, point.y - point.z / 2)
    }

    override fun actRotation(rotatingImage: GameImage, rotationPosition: GameImage, delta: Float) {
        when (rotatingImage.movingState) {
            MovingState.ROTATE_POINT -> {
                if (rotationPosition == NONE) {
                    rotatingImage.movingState = MovingState.NONE
                } else {
                    angle += slice * rotatingImage.movingSpeed * delta
                    setRotationPosition(rotatingImage, getRotationPoint(rotatingImage, rotationPosition, angle))
                }
            }
            MovingState.MOVING -> {
                val degrees = Math.atan2(
                        (rotationPosition.getCenterY() - rotatingImage.getCenterY()).toDouble(),
                        (rotationPosition.getCenterX() - rotatingImage.getCenterX()).toDouble()
                ) * ONE_EIGHTY_DEGREE / Math.PI

                rotatingImage.rotation = (degrees + -NINETY_DEGREE).toFloat()
                currentRotation = rotatingImage.rotation - rotationPosition.rotation
            }
            MovingState.NONE -> {
                if (rotationPosition != GameImage.NONE) {
                    rotatingImage.rotation = rotationPosition.rotation + currentRotation
                }

            }
        }
    }
    override fun actRotation(rotatingImage: GameImage, rotatePosition: Rectangle, delta: Float) {
        //TODO  mach das allgemeiner
    }

    override fun setRotating(gameImage: GameObject, rotateImage: GameImage, radius: Double) {
        gameImage.getGameImage().followImage = rotateImage
        setRotationRadius(radius)
        gameImage.getGameImage().movingState = MovingState.ROTATE_POINT
    }

}