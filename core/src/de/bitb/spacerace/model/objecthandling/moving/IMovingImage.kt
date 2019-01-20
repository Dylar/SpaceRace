package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Action
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData

interface IMovingImage {

    fun moveTo(movingImage: GameImage, imagePosition: PositionData, vararg doAfter: Action)
    fun moveToPoint(movingImage: GameImage, targetImage: GameImage, vararg doAfter: Action)
    fun moveToPoint(movingImage: GameImage, targetPoint: Rectangle, vararg doAfter: Action)

    fun actMovingTo(movingImage: GameImage, targetPosition: GameImage, deltaTime: Float)
    fun actMovingTo(movingImage: GameImage, targetPosition: Rectangle, deltaTime: Float)
}