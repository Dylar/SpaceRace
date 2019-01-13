package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import de.bitb.spacerace.config.ROTATION_MOVING_SPEED
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.utils.CalculationUtils

class RotatingImage(var speed: Double = Math.random()) : IRotatingImage {

    private val slice: Float = (2 * Math.PI / ROTATION_MOVING_SPEED).toFloat()
    private var angle = 0.0
    private var radius: Double = 0.0
    private var rotationPoint: PositionData = PositionData()

    override fun setRotationRadius(radius: Double) {
        this.radius = radius
    }

    override fun setRotationPoint(rotationPoint: PositionData) {
        this.rotationPoint = rotationPoint.copy()
    }

    override fun getRotationAction(gameImage: GameImage, targetPosition: PositionData): RunnableAction {
        val point = getRotationPosition(gameImage, targetPosition, angle)

        return gameImage.getRunnableAction(Runnable {
            rotationPoint = targetPosition
            setRotationPosition(gameImage, point)
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

    override fun getRotationPosition(gameImage: GameImage, rotationPoint: PositionData, angle: Double): Vector2 {
        val posX = rotationPoint.posX + rotationPoint.width / 2 - gameImage.width / 2
        val posY = rotationPoint.posY + rotationPoint.height / 2 - gameImage.height / 2
        return getRotationPoint(posX, posY, angle)
    }

//    override fun getRotationPosition(gameImage: GameImage): Vector2 {
//        return getRotationPosition(gameImage, rotationPoint, angle)
//    }

    override fun actRotation(gameImage: GameImage, delta: Float) {
        when (gameImage.movingState) {
            MovingState.ROTATE_POINT -> {
                angle += slice * speed * delta
                setRotationPosition(gameImage, getRotationPosition(gameImage, rotationPoint, angle))
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