package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import de.bitb.spacerace.model.enums.FieldType

abstract class GameImage(val texture: Texture) : Image(texture) {

    private val actionQueue: MutableList<Action> = ArrayList()

    open fun isIdling(): Boolean {
        return actions.size == 0
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
                addAction(seq)
            }
        }
        return check
    }
}
