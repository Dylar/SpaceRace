package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Vector2
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

interface IRotatingImage {
    fun setRotationPosition(gameImage: GameImage, rotationPoint: PositionData, delta: Float)
    fun setRotationPosition(gameImage: GameImage, point: Vector2)

    fun getRotationPosition(gameImage: GameImage, rotationPoint: PositionData): Vector2
    fun getRotationPosition(posX: Float, posY: Float, rotationPoint: PositionData, angle: Double): Vector2
    fun getRotationPosition(gameImage: GameImage, rotationPoint: PositionData, angle: Double): Vector2
}