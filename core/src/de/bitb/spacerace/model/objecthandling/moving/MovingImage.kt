package de.bitb.spacerace.model.objecthandling.moving

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import de.bitb.spacerace.config.GAME_SPEED
import de.bitb.spacerace.model.objecthandling.GameImage
import de.bitb.spacerace.model.objecthandling.PositionData

class MovingImage : IMovingImage {

    override fun moveTo(movingObject: GameImage, positionData: PositionData) {
        val moveTo = MoveToAction()
        moveTo.setPosition(positionData.posX + movingObject.positionData.width / 2, positionData.posY + movingObject.positionData.height / 2)
        moveTo.duration = getDurationToTarget(movingObject, positionData)
        movingObject.addAction(moveTo)
        movingObject.positionData.setPosition(positionData)
    }

    override fun getDistanceToTarget(movingObject: GameImage, positionData: PositionData): Float {
        val vector = Vector2(positionData.posX + positionData.width / 2, positionData.posY + positionData.height / 2).sub(Vector2(movingObject.positionData.posX + movingObject.positionData.width / 2, movingObject.positionData.posY + movingObject.positionData.height / 2))
        return Math.sqrt((vector.x * vector.x + vector.y * vector.y).toDouble()).toFloat()
    }

    override fun getDurationToTarget(movingObject: GameImage, positionData: PositionData): Float {
        return ((getDistanceToTarget(movingObject, positionData) / movingObject.positionData.movingSpeed) / GAME_SPEED.speed)
    }
}