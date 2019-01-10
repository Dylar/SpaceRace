package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import de.bitb.spacerace.base.BaseObject
import de.bitb.spacerace.base.BaseScreen.Companion.MAIN_DELTA
import de.bitb.spacerace.config.BLINKING_INTERVAL
import de.bitb.spacerace.config.GAME_SPEED

class DefaultImage : IDefaultImage {

    var blinkingColor: Color? = null
    private var blinkDelta: Float = MAIN_DELTA

    override fun setBlinkColor(blinkColor: Color?) {
        blinkingColor = blinkColor
    }


    override fun getBlinkColor(delta: Float, currentColor: Color): Color? {
        return when {
            blinkingColor != null -> {
                var blinkColor = currentColor
                if (heartBeat()) {
                    blinkColor = if (currentColor == blinkingColor) Color.WHITE else blinkingColor!!
                }
                blinkColor
            }
            else -> null
        }
    }

    private fun heartBeat(): Boolean {
        if(0.6 < MAIN_DELTA){
            blinkDelta = MAIN_DELTA
        }

        if (0.6 > MAIN_DELTA) {
            blinkDelta = 1f
            return true
        }

        return false
    }

    override fun moveTo(movingObject: BaseObject, targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float) {
        val moveTo = MoveToAction()
        moveTo.setPosition(targetX - movingObject.width / 2, targetY - movingObject.height / 2)
        moveTo.duration = getDurationToTarget(movingObject, targetX, targetY, targetWidth, targetHeight)
        movingObject.addAction(moveTo)
    }

    override fun getDistanceToTarget(movingObject: BaseObject, targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float): Float {
        val vector = Vector2(targetX + targetWidth / 2, targetY + targetHeight / 2).sub(Vector2(movingObject.x + movingObject.width / 2, movingObject.y + movingObject.height / 2))
        return Math.sqrt((vector.x * vector.x + vector.y * vector.y).toDouble()).toFloat()
    }

    override fun getDurationToTarget(movingObject: BaseObject, targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float): Float {
        return ((getDistanceToTarget(movingObject, targetX, targetY, targetWidth, targetHeight) / movingObject.movingSpeed) / GAME_SPEED.speed)
    }
}