package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData

interface IRotatingImage {
    fun setRotationRadius(radius: Double)
    fun setRotationPoint(rotationPoint: PositionData)
    fun setRotationPosition(gameImage: GameImage, point: Vector2)
    fun setRotationFollow(gameImage: GameImage?)

    fun getRotationPoint(gameImage: GameImage, rotationPoint: PositionData, angle: Double): Vector2
    fun getRotationPosition(gameImage: GameImage, targetPosition: PositionData): PositionData
    fun getRotationAction(gameImage: GameImage, targetPosition: PositionData): RunnableAction
    fun getRotationAction(gameImage: GameImage, followImage: GameImage): RunnableAction

    fun actRotation(gameImage: GameImage, delta: Float)
}