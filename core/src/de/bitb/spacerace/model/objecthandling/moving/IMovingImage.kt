package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.scenes.scene2d.Action
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

interface IMovingImage {

    fun moveTo(movingImage: GameImage, imagePosition: PositionData, vararg doAfter: Action)
    fun moveToPoint(movingImage: GameImage, targetImage: GameImage, vararg doAfter: Action)

    fun actMoving(movingImage: GameImage, targetPosition: GameImage, deltaTime: Float)
}