package de.bitb.spacerace.model.objecthandling.rotating

import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.GameObject
import de.bitb.spacerace.model.objecthandling.PositionData

interface IRotatingImage {
    fun setPosition(gameImage: GameImage, rotationPoint: PositionData, delta: Float)
}