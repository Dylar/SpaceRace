package de.bitb.spacerace.model.objecthandling.rotating

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData
import de.bitb.spacerace.model.player.PlayerImage

interface IRotatingImage {

    fun setRotationRadius(radius: Double)
    fun setRotationPosition(gameImage: GameImage, point: Vector3)
    fun setRotationPoint(gameImage: GameObject, followImage: GameImage, radius: Double)

    fun getRotationAngle(): Double
    fun getRotationPoint(gameImage: GameImage, followImage: GameImage, angle: Double): Vector3
    fun getRotationPosition(gameImage: GameImage, targetPosition: GameImage): PositionData

    fun getRotationAction(gameImage: GameImage, followImage: GameImage): RunnableAction
    fun getRotationAction(gameImage: PlayerImage, targetPosition: Vector2): RunnableAction

    fun actRotation(gameImage: GameImage, delta: Float)
    fun getNONEAction(gameImage: GameImage, followImage: GameImage): RunnableAction
}