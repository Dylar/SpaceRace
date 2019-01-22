package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

interface IRotatingImage {

    fun setRotationRadius(radius: Double)
    fun getRotationPosition(gameImage: GameImage, targetPosition: GameImage): PositionData

    fun actRotation(rotatingImage: GameImage, rotationPosition: GameImage, delta: Float)
    fun actRotation(rotatingImage: GameImage, rotatePosition: Rectangle, delta: Float)
    fun setRotating(gameImage: GameObject, rotateImage: GameImage, radius: Double)

    fun getRotationAction(gameImage: GameImage, followImage: GameImage): RunnableAction
    fun getNONEAction(gameImage: GameImage, followImage: GameImage): RunnableAction {
        return gameImage.getRunnableAction(Runnable {
            gameImage.followImage = followImage
            gameImage.movingState = MovingState.NONE
        })
    }

}