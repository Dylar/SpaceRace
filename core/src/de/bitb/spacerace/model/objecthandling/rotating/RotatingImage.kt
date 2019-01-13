package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import de.bitb.spacerace.config.ROTATION_MOVING_SPEED
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.utils.CalculationUtils

class RotatingImage(var speed: Double = Math.random()) : IRotatingImage {

    private val slice: Float = (2 * Math.PI / ROTATION_MOVING_SPEED).toFloat()
    private var angle = 0.0
    private var radius: Double = 0.0
    private var rotationPoint: PositionData = PositionData()
    var followImage: GameImage? = null

    override fun setRotationFollow(gameImage: GameImage?) {
        followImage = gameImage
    }

    override fun setRotationRadius(radius: Double) {
        this.radius = radius
    }

//    override fun getRotationRadius(): Double {
//        return radius
//    }
//
//    override fun getRotationAngle(): Double {
//        return angle
//    }

    override fun setRotationPoint(rotationPoint: PositionData) {
        this.rotationPoint = rotationPoint.copy()
    }

    override fun getRotationPosition(gameImage: GameImage, targetPosition: PositionData): PositionData {
        val point = getRotationPoint(gameImage, targetPosition, angle)
        return targetPosition.copy(posX = point.x - targetPosition.width / 2, posY = point.y - targetPosition.height / 2)
    }

    override fun getRotationAction(gameImage: GameImage, targetPosition: PositionData): RunnableAction {
        return gameImage.getRunnableAction(Runnable {
            rotationPoint = targetPosition
            gameImage.movingState = MovingState.ROTATE_POINT
        })
    }

    override fun getRotationAction(gameImage: GameImage): RunnableAction {
        return gameImage.getRunnableAction(Runnable {
            gameImage.movingState = MovingState.ROTATE_POINT
        })
    }

    fun getRotationPoint(posX: Float, posY: Float, angle: Double): Vector2 {
        return CalculationUtils.calculateRotationPoint(
                Vector2(posX, posY),
                if (radius == 0.0) rotationPoint.width * 0.7 else radius,
                angle
        )
    }

    override fun getRotationPoint(gameImage: GameImage, rotationPoint: PositionData, angle: Double): Vector2 {
        val posX = rotationPoint.posX + rotationPoint.width / 2 - gameImage.width / 2
        val posY = rotationPoint.posY + rotationPoint.height / 2 - gameImage.height / 2
        return getRotationPoint(posX, posY, angle)
    }

//    override fun getRotationPoint(gameImage: GameImage): Vector2 {
//        return getRotationPoint(gameImage, rotationPoint, angle)
//    }

    override fun actRotation(gameImage: GameImage, delta: Float) {
        when (gameImage.movingState) {
            MovingState.ROTATE_POINT -> {
                if (followImage == null) {
                    angle += slice * speed * delta
                    setRotationPosition(gameImage, getRotationPoint(gameImage, rotationPoint, angle))
                } else {
                    setRotationPosition(gameImage, Vector2(followImage!!.x + gameImage.width / 2, followImage!!.y + gameImage.height / 2))
                }
            }
            MovingState.MOVING -> {
            }
            MovingState.NONE -> {
            }
        }
    }

    override fun setRotationPosition(gameImage: GameImage, point: Vector2) {
        gameImage.setPosition(point.x, point.y)
    }
}