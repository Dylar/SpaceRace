package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import de.bitb.spacerace.config.ROTATION_MOVING_SPEED
import de.bitb.spacerace.config.dimensions.Dimensions.NINETY_DEGREE
import de.bitb.spacerace.config.dimensions.Dimensions.ONE_EIGHTY_DEGREE
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameImage.Companion.NONE
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.utils.CalculationUtils


class RotatingImage(var speed: Double = Math.random()) : IRotatingImage {
    private val slice: Float = (2 * Math.PI / ROTATION_MOVING_SPEED).toFloat()
    private var angle = 0.0
    private var radius: Double = 0.0

    override fun getRotationAngle(): Double {
        return angle
    }

    override fun setRotationRadius(radius: Double) {
        this.radius = radius
    }

    override fun getRotationAction(gameImage: GameImage, followImage: GameImage): RunnableAction {
        return gameImage.getRunnableAction(Runnable {
            gameImage.followImage = followImage
            gameImage.movingState = MovingState.ROTATE_POINT
        })
    }

    fun getRotationPoint(posX: Float, posY: Float, angle: Double): Vector3 {
        return CalculationUtils.calculateRotationPoint(
                Vector2(posX, posY),
                radius,
                angle
        )
    }

    override fun getRotationPosition(gameImage: GameImage, targetPosition: GameImage): PositionData {
        val point = getRotationPoint(gameImage, targetPosition, angle)
//        val position = PositionData(posX = point.x - targetPosition.width / 2, posY = point.y - targetPosition.height / 2)
        val position = PositionData(posX = point.x, posY = point.y) //TODO so?
        return position
    }

    override fun getRotationPoint(gameImage: GameImage, followImage: GameImage, angle: Double): Vector3 {
        val posX = followImage.getCenterX() - gameImage.width / 2
        val posY = followImage.getCenterY() - gameImage.height / 2
        return getRotationPoint(posX, posY, angle)
    }

    override fun actRotation(rotatingImage: GameImage, rotationPosition: GameImage, delta: Float) {
        when (rotatingImage.movingState) {
            MovingState.ROTATE_POINT -> {
                if (rotationPosition == NONE) {
                    rotatingImage.movingState = MovingState.NONE
                } else {
                    angle += slice * speed * delta
                    setRotationPosition(rotatingImage, getRotationPoint(rotatingImage, rotationPosition, angle))
                }
            }
            MovingState.MOVING -> {
                val degrees = Math.atan2(
                        (rotationPosition.getCenterY() - rotatingImage.getCenterY()).toDouble(),
                        (rotationPosition.getCenterX() - rotatingImage.getCenterX()).toDouble()
                ) * ONE_EIGHTY_DEGREE / Math.PI

                rotatingImage.rotation = (degrees + -NINETY_DEGREE).toFloat()
            }
            MovingState.NONE -> {
            }
        }
    }

    override fun setRotationPosition(gameImage: GameImage, point: Vector3) {
        gameImage.setPosition(point.x - point.z / 2, point.y - point.z / 2)
    }

    override fun setRotationPoint(gameImage: GameObject, followImage: GameImage, radius: Double) {
        gameImage.getGameImage().followImage = followImage
        setRotationRadius(radius)
        gameImage.getGameImage().movingState = MovingState.ROTATE_POINT
    }

}