package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Vector2
import de.bitb.spacerace.config.ROTATION_MOVING_SPEED
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.objecthandling.moving.IMovingImage
import de.bitb.spacerace.model.objecthandling.moving.MovingImage
import de.bitb.spacerace.utils.CalculationUtils

class RotatingImage(var speed: Double = Math.random()) : IRotatingImage, IMovingImage by MovingImage() {

//    init {
//        setOrigin(width / 2, height / 2)
//        color = imageColor
//    }

    var angle = 0.0
    var point = Vector2()

    private val slice: Float = (2 * Math.PI / ROTATION_MOVING_SPEED).toFloat()

    override fun setPosition(gameImage: GameImage, rotationPoint: PositionData, delta: Float) {
        angle += slice * speed * delta
        val posX = rotationPoint.posX + rotationPoint.width / 2 - gameImage.width / 2
        val posY = rotationPoint.posY + rotationPoint.height / 2 - gameImage.height / 2
        point = CalculationUtils.calculateRotationPoint(
                Vector2(posX, posY),
                rotationPoint.width * .7,
                angle
        )
        gameImage.setPosition(point.x, point.y)
    }

//    fun moveTo(gameObject: GameObject, rotationPoint: PositionData) {
//        val moveTo = MoveToAction()
//        val point = CalculationUtils.calculateRotationPoint(Vector2(spaceField.rotationPoint.posX - width / 2, spaceField.rotationPoint.posY - height / 2), (width * 2).toDouble())
//
//        moveTo.setPosition(point.x, point.y)
//        moveTo.duration = (5f * speed).toFloat()
//        addAction(moveTo)
//    }
}