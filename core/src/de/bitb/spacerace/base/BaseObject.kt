package de.bitb.spacerace.base

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import de.bitb.spacerace.config.GAME_SPEED
import de.bitb.spacerace.config.MOVING_SPEED
import de.bitb.spacerace.model.objecthandling.DefaultFunction
import de.bitb.spacerace.model.objecthandling.DefaultImage
import de.bitb.spacerace.model.objecthandling.IDefaultImage
import kotlin.collections.ArrayList

open class BaseObject(val img: Texture) :
        Image(img),
        DefaultFunction by object : DefaultFunction {},
        IDefaultImage by DefaultImage() {

    private val actionQueue: MutableList<Action> = ArrayList()
    open var movingSpeed: Float = MOVING_SPEED

    init {
        setBounds(x, y, width, height)
        setOrigin(Align.center)
    }

    open fun getAbsolutX(): Float {
        return x
    }

    open fun getAbsolutY(): Float {
        return y
    }

    //Stats

    //State
    fun isIdling(): Boolean {
        return actions.size == 0
    }

    //Actions
    fun moveTo(target: BaseObject) {
        moveTo(target.getAbsolutX(), target.getAbsolutY(), target.width, target.height)
    }

    fun moveTo(targetX: Float, targetY: Float, targetWidth: Float = 0f, targetHeight: Float = 0f) {
        val moveTo = MoveToAction()
        moveTo.setPosition(targetX - width / 2, targetY - height / 2)
        moveTo.duration = getDurationToTarget(this,targetX, targetY, targetWidth, targetHeight)
        addAction(moveTo)
    }

    override fun addAction(action: Action?) {
        if (isIdling()) {
            super.addAction(Actions.sequence(action, getCheckAction()))
        } else {
            actionQueue.add(action!!)
        }
    }

    fun addAction(runnable: Runnable) {
        val action = RunnableAction()
        action.runnable = runnable
        addAction(action)
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
                super.addAction(seq)
            }
        }
        return check
    }
}