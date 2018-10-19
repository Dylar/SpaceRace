package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.GAME_SPEED
import de.bitb.spacerace.Logger
import de.bitb.spacerace.MOVING_SPEED
import kotlin.collections.ArrayList

open class BaseObject(img: Texture) : Image(img) {

    private val actionQueue: MutableList<Action> = ArrayList()
    open var movingSpeed: Float = de.bitb.spacerace.MOVING_SPEED

    init {
        setBounds(x, y, width, height)
//        setScale(0.5f)
    }

    //Stats

    //State
    fun isIdling(): Boolean {
        return actions.size == 0
    }

    //Actions
    fun moveTo(target: BaseObject) {
        moveTo(target.x, target.y, target.width, target.height)
    }

    fun moveTo(targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float) {
        val move = MoveToAction()
        move.setPosition(targetX + targetWidth / 2 - width / 2, targetY + targetHeight / 2 - height / 2)
        move.duration = getDurationToTarget(targetX, targetY, targetWidth, targetHeight)

        Logger.println("Duration: ${move.duration}, Distance: ${getDistanceToTarget(targetX, targetY, targetWidth, targetHeight)}")
        if (isIdling()) {
            addAction(Actions.sequence(move, getCheckAction()))
        } else {
            actionQueue.add(move)
        }
    }

    private fun getCheckAction(): Action {
        val check = RunnableAction()
        check.runnable = Runnable {
            @Synchronized
            if (!actionQueue.isEmpty()) {
                val seq = Actions.sequence()
                for (action in actionQueue) {
                    seq.addAction(action)
                }
                seq.addAction(getCheckAction())
                actionQueue.clear()
                addAction(seq)
            }
        }
        return check
    }

    private fun getDistanceToTarget(targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float): Float {
        val vector = Vector2(targetX + targetWidth / 2, targetY + targetHeight / 2).sub(Vector2(x + width / 2, y + height / 2))
        return Math.sqrt((vector.x * vector.x + vector.y * vector.y).toDouble()).toFloat()
    }

    private fun getDurationToTarget(targetX: Float, targetY: Float, targetWidth: Float, targetHeight: Float): Float {
        return ((getDistanceToTarget(targetX, targetY, targetWidth, targetHeight) / movingSpeed) / GAME_SPEED)
    }

}