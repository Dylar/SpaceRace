package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData

interface IMovingImage {

    fun moveTo(movingImage: GameImage, imagePosition: PositionData, vararg doAfter: Action)
    fun moveToPoint(movingImage: GameImage, targetImage: GameImage, vararg doAfter: Action)
    fun moveToPoint(movingImage: GameImage, targetPoint: Rectangle, vararg doAfter: Action)
    fun patrollingBetweenPoints(movingImage: GameImage, targetPoint1: Rectangle, targetPoint2: Rectangle, vararg doAfter: Action)

    fun actMovingTo(delta: Float, movingImage: GameImage, targetPosition1: GameImage)
    fun actMovingTo(delta: Float, movingImage: GameImage, targetPosition1: Rectangle)

}