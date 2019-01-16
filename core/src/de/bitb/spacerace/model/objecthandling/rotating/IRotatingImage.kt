package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import de.bitb.spacerace.model.items.disposable.moving.MovingState
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

interface IRotatingImage {

    fun setRotationRadius(radius: Double)
    fun setRotationPosition(gameImage: GameImage, point: Vector3)
    fun setRotationPoint(gameImage: GameObject, followImage: GameImage, radius: Double)

    fun getRotationAngle(): Double
    fun getRotationPosition(gameImage: GameImage, targetPosition: GameImage): PositionData
    fun getRotationPoint(gameImage: GameImage, followImage: GameImage, angle: Double): Vector3

    fun getRotationAction(gameImage: GameImage, followImage: GameImage): RunnableAction
    fun actRotation(gameImage: GameImage, delta: Float)

    fun getNONEAction(gameImage: GameImage, followImage: GameImage): RunnableAction {
        return gameImage.getRunnableAction(Runnable {
            gameImage.followImage = followImage
            gameImage.movingState = MovingState.NONE
        })
    }
}