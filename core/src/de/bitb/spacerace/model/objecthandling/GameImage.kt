package de.bitb.spacerace.model.objecthandling

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import de.bitb.spacerace.config.DEBUG_FIELDS
import de.bitb.spacerace.core.TextureCollection
import de.bitb.spacerace.model.enums.FieldType

abstract class GameImage(val texture: Texture) : Image(texture) {

    var idlingCount = 0
    private val actionQueue: MutableList<Action> = ArrayList()

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        if (DEBUG_FIELDS) {
            val label = TextButton("IAMGE: MACH DIE NUMMER", TextureCollection.skin, "default")
            label.label.width = width
            label.setPosition(x, y)
            label.color = Color.ROYAL
            label.style.fontColor = Color.RED
            label.draw(batch, parentAlpha)
        }
    }

    fun isIdling(): Boolean {
        return actions.size == idlingCount
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
