package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Vector2
import de.bitb.spacerace.config.ROTATION_MOVING_SPEED
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.utils.CalculationUtils

class RotatingImage(var speed: Double = Math.random()) : IRotatingImage, IMovingImage by MovingImage() {

    private val slice: Float = (2 * Math.PI / ROTATION_MOVING_SPEED).toFloat()
    private var angle = 0.0

    override fun setRotationPosition(gameImage: GameImage, rotationPoint: PositionData, delta: Float) {
        angle += slice * speed * delta
        setRotationPosition(gameImage, getRotationPosition(gameImage, rotationPoint))
    }

    override fun setRotationPosition(gameImage: GameImage, point: Vector2) {
        gameImage.setPosition(point.x, point.y)
    }

    override fun getRotationPosition(gameImage: GameImage, rotationPoint: PositionData): Vector2 {
        return getRotationPosition(gameImage, rotationPoint, angle)
    }

    override fun getRotationPosition(posX: Float, posY: Float, rotationPoint: PositionData, angle: Double): Vector2 {
        return CalculationUtils.calculateRotationPoint(
                Vector2(posX, posY),
                rotationPoint.width * .7,
                angle
        )
    }

    override fun getRotationPosition(gameImage: GameImage, rotationPoint: PositionData, angle: Double): Vector2 {
        val posX = rotationPoint.posX + rotationPoint.width / 2 - gameImage.width / 2
        val posY = rotationPoint.posY + rotationPoint.height / 2 - gameImage.height / 2
        return getRotationPosition(posX, posY, rotationPoint, angle)
    }
}